package parsers;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Locale;

 public class CmdLineParser 
  {

   public static abstract class OptionException extends Exception 
    {OptionException(String msg) { super(msg); }}

    
    public static class UnknownOptionException extends OptionException {
        UnknownOptionException( String optionName ) {
            this(optionName, "Unknown option '" + optionName + "'");
        }

        UnknownOptionException( String optionName, String msg ) {
            super(msg);
            this.optionName = optionName;
        }

        /**
         * @return the name of the option that was unknown (e.g. "-u")
         */
        public String getOptionName() { return this.optionName; }
        private String optionName = null;
    }

    
    public static class UnknownSuboptionException
        extends UnknownOptionException {
        private char suboption;

        UnknownSuboptionException( String option, char suboption ) {
            super(option, "Illegal option: '"+suboption+"' in '"+option+"'");
            this.suboption=suboption;
        }
        public char getSuboption() { return suboption; }
    }

    
    public static class NotFlagException extends UnknownOptionException {
        private char notflag;

        NotFlagException( String option, char unflaggish ) {
            super(option, "Illegal option: '"+option+"', '"+
                  unflaggish+"' requires a value");
            notflag=unflaggish;
        }

        
        public char getOptionChar() { return notflag; }
    }

    
    public static class IllegalOptionValueException extends OptionException {
        public IllegalOptionValueException( Option opt, String value ) {
            super("Illegal value '" + value + "' for option " +
                  (opt.shortForm() != null ? "-" + opt.shortForm() + "/" : "") +
                  "--" + opt.longForm());
            this.option = opt;
            this.value = value;
        }

        
        public Option getOption() { return this.option; }

        
        public String getValue() { return this.value; }
        private Option option;
        private String value;
    }

    
    public static abstract class Option {

        protected Option( String longForm, boolean wantsValue ) {
            this(null, longForm, wantsValue);
        }

        protected Option( char shortForm, String longForm,
                          boolean wantsValue ) {
            this(new String(new char[]{shortForm}), longForm, wantsValue);
        }

        private Option( String shortForm, String longForm, boolean wantsValue ) {
            if ( longForm == null )
                throw new IllegalArgumentException("Null longForm not allowed");
            this.shortForm = shortForm;
            this.longForm = longForm;
            this.wantsValue = wantsValue;
        }

        public String shortForm() { return this.shortForm; }

        public String longForm() { return this.longForm; }

        
        public boolean wantsValue() { return this.wantsValue; }

        public final Object getValue( String arg, Locale locale )
            throws IllegalOptionValueException {
            if ( this.wantsValue ) {
                if ( arg == null ) {
                    throw new IllegalOptionValueException(this, "");
                }
                return this.parseValue(arg, locale);
            }
            else {
                return Boolean.TRUE;
            }
        }

        
        protected Object parseValue( String arg, Locale locale )
            throws IllegalOptionValueException {
            return null;
        }

        private String shortForm = null;
        private String longForm = null;
        private boolean wantsValue = false;

        public static class BooleanOption extends Option {
            public BooleanOption( char shortForm, String longForm ) {
                super(shortForm, longForm, false);
            }
            public BooleanOption( String longForm ) {
                super(longForm, false);
            }
        }

        
        public static class IntegerOption extends Option {
            public IntegerOption( char shortForm, String longForm ) {
                super(shortForm, longForm, true);
            }
            public IntegerOption( String longForm ) {
                super(longForm, true);
            }
            protected Object parseValue( String arg, Locale locale )
                throws IllegalOptionValueException {
                try {
                    return new Integer(arg);
                }
                catch (NumberFormatException e) {
                    throw new IllegalOptionValueException(this, arg);
                }
            }
        }

        
        public static class LongOption extends Option {
            public LongOption( char shortForm, String longForm ) {
                super(shortForm, longForm, true);
            }
            public LongOption( String longForm ) {
                super(longForm, true);
            }
            protected Object parseValue( String arg, Locale locale )
                throws IllegalOptionValueException {
                try {
                    return new Long(arg);
                }
                catch (NumberFormatException e) {
                    throw new IllegalOptionValueException(this, arg);
                }
            }
        }

        
        public static class DoubleOption extends Option {
            public DoubleOption( char shortForm, String longForm ) {
                super(shortForm, longForm, true);
            }
            public DoubleOption( String longForm ) {
                super(longForm, true);
            }
            protected Object parseValue( String arg, Locale locale )
                throws IllegalOptionValueException {
                try {
                    NumberFormat format = NumberFormat.getNumberInstance(locale);
                    Number num = (Number)format.parse(arg);
                    return new Double(num.doubleValue());
                }
                catch (ParseException e) {
                    throw new IllegalOptionValueException(this, arg);
                }
            }
        }

        
        public static class StringOption extends Option {
            public StringOption( char shortForm, String longForm ) {
                super(shortForm, longForm, true);
            }
            public StringOption( String longForm ) {
                super(longForm, true);
            }
            protected Object parseValue( String arg, Locale locale ) {
                return arg;
            }
        }
    }

    
    public final Option addOption( Option opt ) {
        if ( opt.shortForm() != null )
            this.options.put("-" + opt.shortForm(), opt);
        this.options.put("--" + opt.longForm(), opt);
        return opt;
    }

    
    public final Option addStringOption( char shortForm, String longForm ) {
        return addOption(new Option.StringOption(shortForm, longForm));
    }

    
    public final Option addStringOption( String longForm ) {
        return addOption(new Option.StringOption(longForm));
    }

    
    public final Option addIntegerOption( char shortForm, String longForm ) {
        return addOption(new Option.IntegerOption(shortForm, longForm));
    }

    
    public final Option addIntegerOption( String longForm ) {
        return addOption(new Option.IntegerOption(longForm));
    }

    public final Option addLongOption( char shortForm, String longForm ) {
        return addOption(new Option.LongOption(shortForm, longForm));
    }

    
    public final Option addLongOption( String longForm ) {
        return addOption(new Option.LongOption(longForm));
    }

    
    public final Option addDoubleOption( char shortForm, String longForm ) {
        return addOption(new Option.DoubleOption(shortForm, longForm));
    }

    
    public final Option addDoubleOption( String longForm ) {
        return addOption(new Option.DoubleOption(longForm));
    }

    
    public final Option addBooleanOption( char shortForm, String longForm ) {
        return addOption(new Option.BooleanOption(shortForm, longForm));
    }

    
    public final Option addBooleanOption( String longForm ) {
        return addOption(new Option.BooleanOption(longForm));
    }

    
    public final Object getOptionValue( Option o ) {
        return getOptionValue(o, null);
    }


    
    public final Object getOptionValue( Option o, Object def ) {
        Vector v = (Vector)values.get(o.longForm());

        if (v == null) {
            return def;
        }
        else if (v.isEmpty()) {
            return null;
        }
        else {
            Object result = v.elementAt(0);
            v.removeElementAt(0);
            return result;
        }
    }


    
    public final Vector getOptionValues( Option option ) {
        Vector result = new Vector();

        while (true) {
            Object o = getOptionValue(option, null);

            if (o == null) {
                return result;
            }
            else {
                result.addElement(o);
            }
        }
    }


    
    public final String[] getRemainingArgs() {
        return this.remainingArgs;
    }

    
    public final void parse( String[] argv )
        throws IllegalOptionValueException, UnknownOptionException {

        // It would be best if this method only threw OptionException, but for
        // backwards compatibility with old user code we throw the two
        // exceptions above instead.

        parse(argv, Locale.getDefault());
    }

    
    public final void parse( String[] argv, Locale locale )
        throws IllegalOptionValueException, UnknownOptionException {

        // It would be best if this method only threw OptionException, but for
        // backwards compatibility with old user code we throw the two
        // exceptions above instead.

        Vector otherArgs = new Vector();
        int position = 0;
        this.values = new Hashtable(10);
        while ( position < argv.length ) {
            String curArg = argv[position];
            if ( curArg.startsWith("-") ) {
                if ( curArg.equals("--") ) { // end of options
                    position += 1;
                    break;
                }
                String valueArg = null;
                if ( curArg.startsWith("--") ) { // handle --arg=value
                    int equalsPos = curArg.indexOf("=");
                    if ( equalsPos != -1 ) {
                        valueArg = curArg.substring(equalsPos+1);
                        curArg = curArg.substring(0,equalsPos);
                    }
                } else if(curArg.length() > 2) {  // handle -abcd
                    for(int i=1; i<curArg.length(); i++) {
                        Option opt=(Option)this.options.get
                            ("-"+curArg.charAt(i));
                        if(opt==null) throw new 
                            UnknownSuboptionException(curArg,curArg.charAt(i));
                        if(opt.wantsValue()) throw new
                            NotFlagException(curArg,curArg.charAt(i));
                        addValue(opt, opt.getValue(null,locale));
                        
                    }
                    position++;
                    continue;
                }
                
                Option opt = (Option)this.options.get(curArg);
                if ( opt == null ) {
                    throw new UnknownOptionException(curArg);
                }
                Object value = null;
                if ( opt.wantsValue() ) {
                    if ( valueArg == null ) {
                        position += 1;
                        if ( position < argv.length ) {
                            valueArg = argv[position];
                        }
                    }
                    value = opt.getValue(valueArg, locale);
                }
                else {
                    value = opt.getValue(null, locale);
                }

                addValue(opt, value);

                position += 1;
            }
            else {
                otherArgs.addElement(curArg);
                position += 1;
            }
        }
        for ( ; position < argv.length; ++position ) {
            otherArgs.addElement(argv[position]);
        }

        this.remainingArgs = new String[otherArgs.size()];
        otherArgs.copyInto(remainingArgs);
    }


    private void addValue(Option opt, Object value) {
        String lf = opt.longForm();

        Vector v = (Vector)values.get(lf);

        if (v == null) {
            v = new Vector();
            values.put(lf, v);
        }

        v.addElement(value);
    }


    private String[] remainingArgs = null;
    private Hashtable options = new Hashtable(10);
    private Hashtable values = new Hashtable(10);
}
