package net.ion.framework.db;


import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.sql.RowSet;

import net.ion.framework.db.rowset.WebRowSet;
import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: I-ON Communications</p>
 * @author bleujin
 * @version 1.0
 */

public class DefaultXmlWriter implements IXmlWriter
{
    protected Writer writer;
    protected Stack stack;
    final static int CUT_SIZE = 4000;

    protected String[] columnLabel;

    public DefaultXmlWriter() {
    }

    protected void beginSection(String s) throws IOException {
        setTag(s);
        writeIndent(stack.size());
        writer.write("<" + s + ">\n");
    }

    protected void beginTag(String s) throws IOException {
        setTag(s);
        writeIndent(stack.size());
        writer.write("<" + s + ">");
    }

    protected void emptyTag(String s) throws IOException {
        writer.write("<" + s + "/>");
    }

    protected void endSection() throws IOException {
        writeIndent(stack.size());
        String s = getTag();
        writer.write("</" + s + ">\n");
        writer.flush();
    }

    protected void endSection(String s) throws IOException {
        writeIndent(stack.size());
        String s1 = getTag();
        if(s.equals(s1))
            writer.write("</" + s1 + ">\n");
        writer.flush();
    }

    protected void endTag(String s) throws IOException {
        String s1 = getTag();
        if(s.equals(s1))
            writer.write("</" + s1 + ">\n");
        writer.flush();
    }

    protected String getTag() {
        return(String)stack.pop();
    }

    protected void propBoolean(String s, boolean flag) throws IOException {
        beginTag(s);
        writeBoolean(flag);
        endTag(s);
    }

    protected void propInteger(String s, int i) throws IOException {
        beginTag(s);
        writeInteger(i);
        endTag(s);
    }

    protected void propString(String s, String s1) throws IOException {
        beginTag(s);
        writeString(s1);
        endTag(s);
    }

    protected void setTag(String s) {
        stack.push(s);
    }

    protected void writeBigDecimal(BigDecimal bigdecimal) throws IOException {
        if(bigdecimal != null)
            writer.write(bigdecimal.toString());
        else
            writeNull();
    }

    protected void writeBoolean(boolean flag) throws IOException {
        writer.write((new Boolean(flag)).toString());
    }

    protected void writeClob(String data) throws IOException {
        if(data != null) {
            startCDATATag();
            writer.write(data);
            endCDATATag();
        } else
            writeNull();
    }

    protected void startCDATATag() throws IOException {
        writer.write("<![CDATA[");
    }

    protected void endCDATATag() throws IOException {
        writer.write("]]>");
    }

    protected void writeData(RowSet webrowset) throws IOException {
        try {
            ResultSetMetaData resultsetmetadata = webrowset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            beginSection("data");
            webrowset.beforeFirst();
            for(; webrowset.next(); endSection()) {
                if(webrowset.rowDeleted() && webrowset.rowInserted())
                    beginSection("insdel");
                else if(webrowset.rowDeleted())
                    beginSection("del");
                else
                if(webrowset.rowInserted())
                    beginSection("ins");
                else
                    beginSection("row");
                for(int j = 1; j <= i; j++) {
                    beginTag(columnLabel[j]);
                    writeValue(j, webrowset);
                    endTag(columnLabel[j]);
                }

            }

            endSection("data");
        } catch(SQLException sqlexception) {
            throw new IOException("SQLException: " + sqlexception.getMessage());
        }
    }

    protected void writeDouble(double d) throws IOException {
        writer.write(Double.toString(d));
    }

    protected void writeFloat(float f) throws IOException {
        writer.write(Float.toString(f));
    }

    protected void writeHeader() throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<!DOCTYPE RowSet PUBLIC '" + Rows.PUBLIC_DTD_ID + "' '" + Rows.SYSTEM_ID + "'>\n\n");
    }

    protected void writeIndent(int i) throws IOException {
        for(int j = 1; j < i; j++)
            writer.write("  ");

    }

    protected void writeInteger(int i) throws IOException {
        writer.write(Integer.toString(i));
    }

    protected void writeLong(long l) throws IOException {
        writer.write(Long.toString(l));
    }

    protected void writeMetaData(RowSet webrowset) throws IOException {
        beginSection("metadata");
        try {

            ResultSetMetaData resultsetmetadata = webrowset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            columnLabel = new String[i + 1];


            propInteger("column-count", i);
            for(int j = 1; j <= i; j++) {

                columnLabel[j] = resultsetmetadata.getColumnLabel(j);

                beginSection("column-definition");
                propInteger("column-index", j);
                propBoolean("auto-increment", resultsetmetadata.isAutoIncrement(j));
                propBoolean("case-sensitive", resultsetmetadata.isCaseSensitive(j));
                propBoolean("currency", resultsetmetadata.isCurrency(j));
                propInteger("nullable", resultsetmetadata.isNullable(j));
                propBoolean("signed", resultsetmetadata.isSigned(j));
                propBoolean("searchable", resultsetmetadata.isSearchable(j));
                propInteger("column-display-size", resultsetmetadata.getColumnDisplaySize(j));
                propString("column-label", resultsetmetadata.getColumnLabel(j));
                propString("column-name", resultsetmetadata.getColumnName(j));
                propString("schema-name", resultsetmetadata.getSchemaName(j));
                propInteger("column-precision", resultsetmetadata.getPrecision(j));
                propInteger("column-scale", resultsetmetadata.getScale(j));
                propString("table-name", resultsetmetadata.getTableName(j));
                propString("catalog-name", resultsetmetadata.getCatalogName(j));
                if(resultsetmetadata.getColumnType(j) == 2005) {
                    propInteger("column-type", 12);
                    propString("column-type-name", "VARCHAR2");
                } else {
                    propInteger("column-type", resultsetmetadata.getColumnType(j));
                    propString("column-type-name", resultsetmetadata.getColumnTypeName(j));
                }
                endSection("column-definition");
            }

        } catch(SQLException sqlexception) {
            throw new IOException("SQLException: " + sqlexception.getMessage());
        }
        endSection("metadata");
    }

    protected void writeNull() throws IOException {
        emptyTag("null");
    }

    protected void writeProperties(RowSet webrowset) throws IOException {
        beginSection("properties");
        try {
            propString("command", webrowset.getCommand());
            propInteger("concurrency", webrowset.getConcurrency());
            propString("datasource", webrowset.getDataSourceName());
            propBoolean("escape-processing", webrowset.getEscapeProcessing());
            propInteger("fetch-direction", webrowset.getFetchDirection());
            propInteger("fetch-size", webrowset.getFetchSize());
            propInteger("isolation-level", webrowset.getTransactionIsolation());
            beginTag("map");
            Map map = webrowset.getTypeMap();
            if(map != null) {
                Class class1;
                for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); propString("class", class1.getName())) {
                    String s = (String)iterator.next();
                    class1 = (Class)map.get(s);
                    propString("type", s);
                }

            }
            endTag("map");
            propInteger("max-field-size", webrowset.getMaxFieldSize());
            propInteger("max-rows", webrowset.getMaxRows());
            propInteger("query-timeout", webrowset.getQueryTimeout());
            propBoolean("read-only", webrowset.isReadOnly());
            propInteger("rowset-type", webrowset.getType());
            propString("url", webrowset.getUrl());
        } catch(SQLException sqlexception) {
            throw new IOException("SQLException: " + sqlexception.getMessage());
        }
        endSection("properties");
    }

    protected void writeRowSet(RowSet webrowset) throws SQLException {
        try {
            writeHeader();
            beginSection("RowSet");
            initMeta(webrowset);
            //writeProperties(webrowset);
            //writeMetaData(webrowset);
            writeData(webrowset);
            endSection("RowSet");
        } catch(IOException ioexception) {
            throw new SQLException("IOException: " + ioexception.getMessage());
        }
    }

    private void initMeta(RowSet webrowset) throws SQLException {
        ResultSetMetaData resultsetmetadata = webrowset.getMetaData();
        int i = resultsetmetadata.getColumnCount();
        columnLabel = new String[i + 1];

        for(int j = 1; j <= i; j++) {
            columnLabel[j] = resultsetmetadata.getColumnLabel(j);
        }
    }

    protected void writeShort(short word0) throws IOException {
        writer.write(Short.toString(word0));
    }

    protected void writeString(String s) throws IOException {
        if(s != null) {
            if(!s.equals("")) {
                writer.write(s);
            } else {
                writeNull();
            }
        } else
            writeNull();
    }

    protected void writeValue(int i, RowSet rowset) throws IOException {
        try {
            int j = rowset.getMetaData().getColumnType(i);
            switch(j) {
                case Types.BIT:
                    boolean flag = rowset.getBoolean(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeBoolean(flag);
                    break;

                case Types.LONGVARBINARY:
                case Types.VARBINARY:
                case Types.BINARY:
                    break;

                case Types.SMALLINT: // '\005'
                    short word0 = rowset.getShort(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeShort(word0);
                    break;

                case Types.INTEGER: // '\004'
                    int k = rowset.getInt(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeInteger(rowset.getInt(i));
                    break;

                case Types.BIGINT:
                    long l = rowset.getLong(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeLong(l);
                    break;

                case Types.FLOAT: // '\006'
                case Types.REAL: // '\007'
                    float f = rowset.getFloat(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeFloat(f);
                    break;

                case Types.DOUBLE: // '\b'
                    double d = rowset.getDouble(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeDouble(d);
                    break;

                case Types.NUMERIC: // '\002'
                case Types.DECIMAL: // '\003'
//                    writeBigDecimal( rowset.getBigDecimal( i ) );   -_- zzim zzim..
                    writeString(rowset.getString(i));
                    break;

                case Types.DATE: // '['
//                    java.sql.Date date = rowset.getDate(i);
//                    if(rowset.wasNull())
//                        writeNull();
//                    else
//                        writeLong(date.getTime());
                    writeString(rowset.getString(i));
                    break;

                case Types.TIME: // '\\'
                    java.sql.Time time = rowset.getTime(i);
                    if(rowset.wasNull())
                        writeNull();
                    else
                        writeLong(time.getTime());
                    break;

                case Types.TIMESTAMP: // ']'
                    try {
                        java.sql.Timestamp timestamp = rowset.getTimestamp(i);
                        if(rowset.wasNull()) {
                            writeNull();
                        } else {
                            writeLong(timestamp.getTime());
                        }
                    } catch(ClassCastException ex) {
                        // writeXml 에 의해 만들어진 XML String으로 Rows 를 만든후 writeXml() 하면 ClassCastException 발생 그래서 -_- 이렇게 처리
                        if(rowset.wasNull())
                            writeNull();
                        else
                            writeLong(Long.parseLong(rowset.getString(i)));
                    }
                    break;

                case Types.LONGVARCHAR:
                case Types.CLOB:
                    writeClob(((Rows)rowset).getString(i));
                    break;

                case Types.CHAR: // '\001'
                case Types.VARCHAR: // '\f'
                    writeString(removeTag(rowset.getString(i)));
                    break;
                    
                case Types.OTHER:
                    writeString(removeTag(rowset.getString(i)));
                    break;
                    
                default:
                    Debug.debug("Unknown ColumnType : " + j);
                    break;
            }
        } catch(SQLException sqlexception) {
            throw new IOException("Failed to writeValue: " + sqlexception.getMessage());
        }
    }

    private String removeTag(String s) {
        return StringUtil.filterHTML(s);
    }

    public void writeXML(Rows webrowset, Writer writer) throws SQLException {
        this.stack = new Stack();
        this.writer = writer;
        writeRowSet(webrowset);
    }

    public void writeXML(WebRowSet webrowset, Writer writer) throws SQLException {
        this.stack = new Stack();
        this.writer = writer;
        writeRowSet(webrowset);
    }


}
