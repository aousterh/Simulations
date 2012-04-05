#ifndef XMLPARSER_H
#define XMLPARSER_H

#include "expat.h"

class XMLEvtHandler
{
public:

	XMLEvtHandler();

	virtual void OnPostCreate(void);
	virtual void OnStartElement(const XML_Char* name, const XML_Char** attrs);
	virtual void OnEndElement(const XML_Char* name);
	virtual void OnCharacterData(const XML_Char* data, int len);
	virtual void OnProcessingInstruction(const XML_Char* target, const XML_Char* data);
	virtual void OnComment(const XML_Char* data);
	virtual void OnStartCdataSection(void);
	virtual void OnEndCdataSection(void);
	virtual void OnDefault(const XML_Char* data, int len);
	virtual bool OnUnknownEncoding(const XML_Char* name, XML_Encoding* pInfo);
	virtual void OnStartNamespaceDecl(const XML_Char* prefix, const XML_Char* uri);
	virtual void OnEndNamespaceDecl(const XML_Char* prefix);
	virtual void OnXmlDecl(const XML_Char* version, const XML_Char* encoding, bool isStandalone);
	virtual void OnStartDoctypeDecl(const XML_Char* doctype, const XML_Char* sysId, const XML_Char* pubId, bool hasInternalSubset);
	virtual void OnEndDoctypeDecl(void);
};

class XMLParser
{
private:

	XML_Parser     m_parser;
	XMLEvtHandler* m_pHandler;

public:

	XMLParser(XMLEvtHandler* pHandler);

	~XMLParser();

	
	bool Create(const XML_Char* encoding = NULL, const XML_Char* sep = NULL);

	
	void Destroy(void);

	
	bool Parse(const char* buf, size_t len, bool isFinal = true);
	bool Parse(const char* buf, bool isFinal = true);

	
	bool Parse(size_t len, bool isFinal = true);

	
	void* GetBuffer(size_t len);

	
	void EnableStartElementHandler(bool enable = true);

	
	void EnableEndElementHandler(bool enable = true);

	
	void EnableElementHandler(bool enable = true);

	
	void EnableCharacterDataHandler(bool enable = true);

	
	void EnableProcessingInstructionHandler(bool enable = true);

	
	void EnableCommentHandler(bool enable = true);

	
	void EnableStartCdataSectionHandler(bool enable = true);

	
	void EnableEndCdataSectionHandler(bool enable = true);

	
	void EnableCdataSectionHandler(bool enable = true);

	
	void EnableDefaultHandler(bool enable = true, bool expand = true);

	
	void EnableUnknownEncodingHandler(bool enable = true);

	
	void EnableStartNamespaceDeclHandler(bool enable = true);

	
	void EnableEndNamespaceDeclHandler(bool enable = true);

	
	void EnableNamespaceDeclHandler(bool enable = true);

	
	void EnableXmlDeclHandler(bool enable = true);

	
	void EnableStartDoctypeDeclHandler(bool enable = true);

	
	void EnableEndDoctypeDeclHandler(bool enable = true);

	
	void EnableDoctypeDeclHandler(bool enable = true);

	

	enum XML_Error GetErrorCode(void);

	long GetCurrentByteIndex(void);
	int  GetCurrentLineNumber(void);
	int  GetCurrentColumnNumber(void);
	int  GetCurrentByteCount(void);

	const char* GetInputContext(int* pOffset, int* pSize);
	const XML_LChar* GetErrorString();

	

	static const XML_LChar* GetExpatVersion(void);
	static const XML_LChar* GetErrorString(enum XML_Error err);
};

#endif /* XMLPARSER_H */
