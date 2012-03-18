package gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CheckboxList extends JList
{
 protected static Border noFocusBorder=new EmptyBorder(1, 1, 1, 1);

   public CheckboxList()
    {
     setCellRenderer(new CellRenderer());

     addMouseListener(new MouseAdapter()
      {
       public void mouseClicked(MouseEvent e) 
        {
		 if(e.getClickCount()==2) 
		  {
		   int index=locationToIndex(e.getPoint());

	       if(index!=-1) 
	        {
	         JCheckBox checkbox=(JCheckBox) getModel().getElementAt(index);
	         checkbox.setSelected(!checkbox.isSelected());
	         repaint();
	        }
		  }
        }//Fine mouseClicked
       }
      );//Fine addMouseLIstener
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }//Fine costruttore
   
   public Vector getNameOfCheckBoxSelected()
    {
     Vector selected=new Vector();
     
     for(int i=0;i<getModel().getSize();i++)
      {
       JCheckBox checkBox=(JCheckBox)getModel().getElementAt(i);
       if(checkBox.isSelected())
        { selected.add(checkBox.getText()); } 
      }	   
     return selected;
	}//Fine getSelected
   
   public String getSelectedItem()
    {
	 int index=this.getSelectedIndex();
	 if(index!=-1)
	  {
	   JCheckBox checkbox=(JCheckBox)getModel().getElementAt(index);
	   return checkbox.getText();
	  }
	 return null;
    }//Fine getSelected
   
   public boolean setChecked(String name)
    {
	   int size=getModel().getSize();
	   for(int i=0;i<size;i++)
	    {
		 JCheckBox checkbox=(JCheckBox)getModel().getElementAt(i);
		 //Se i nomi corrispondono seleziono la checkbox
		 if(checkbox.getText().equals(name))
		  {
		   //Seleziono la checkbox
		   checkbox.setSelected(true);
		   return true;
		  }	 
	    }  
	 return false; 
    }//Fine setSelected

   protected class CellRenderer implements ListCellRenderer
    {
     public Component getListCellRendererComponent(
                    JList list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus)
      {
       JCheckBox checkbox=(JCheckBox)value;
       
       checkbox.setBackground(isSelected?getSelectionBackground():getBackground());
       checkbox.setForeground(isSelected?getSelectionForeground():getForeground());
       
       checkbox.setEnabled(isEnabled());
       checkbox.setFont(getFont());
       checkbox.setFocusPainted(false);
       checkbox.setBorderPainted(true);
       checkbox.setBorder(isSelected ?
       
       UIManager.getBorder("List.focusCellHighlightBorder"):noFocusBorder);
       
       return checkbox;
      }
   }//Fine classe interna CellRenderer
}//Fine classe CheckBoxList