package ch.admin.bar.siard2.jdbc;

import java.sql.*;

import static org.junit.Assert.*;

import org.junit.*;

import ch.enterag.utils.*;
import ch.enterag.utils.base.*;
import ch.enterag.utils.jdbc.*;
import ch.enterag.sqlparser.*;
import ch.admin.bar.siard2.db2.*;
import ch.admin.bar.siard2.jdbcx.*;

public class Db2StatementTester extends BaseStatementTester
{
  private static final String _sSQL_DDL = "CREATE TABLE TESTTABLESIMPLE(\r\n"+
    "CCHAR CHARACTER NOT NULL,\r\n" +
    "CVARCHAR VARCHAR(256),\r\n" +
    "CCLOB CLOB(4M),\r\n" +
    "CNCHAR NCHAR,\r\n" +
    "CNCHAR_VARYING NCHAR VARYING(256),\r\n" +
    // "CNCLOB NCLOB(4G),\r\n" +
    "CXML XML,\r\n" +
    "CBINARY BINARY,\r\n" +
    // "CVARBINARY VARBINARY(256),\r\n" +
    "CBLOB BLOB,\r\n" +
    "CNUMERIC NUMERIC(10, 3),\r\n" +
    "CDECIMAL DECIMAL,\r\n" +
    "CSMALLINT SMALLINT,\r\n" +
    "CINTEGER INTEGER NOT NULL,\r\n" +
    "CBIGINT BIGINT,\r\n" +
    "CFLOAT FLOAT(7),\r\n" +
    "CREAL REAL,\r\n" +
    "CDOUBLE DOUBLE PRECISION,\r\n" +
    // "CBOOLEAN BOOLEAN,\r\n" +
    "CDATE DATE,\r\n" +
    // "CTIME TIME(3),\r\n" +
    "CTIMESTAMP TIMESTAMP(9),\r\n" +
    // "CINTERVALYEAR INTERVAL YEAR(2) TO MONTH,\r\n" +
    // "CINTERVALDAY INTERVAL DAY TO MINUTE,\r\n" +
    // "CINTERVALSECOND INTERVAL SECOND(2, 5),\r\n" +
    "PRIMARY KEY(CINTEGER),\r\n" +
    "UNIQUE(CCHAR))";
  private static final String _sSQL_CLEAN = "DROP TABLE TESTTABLESIMPLE CASCADE";
  private static final String _sSQL_QUERY = "SELECT * FROM SYSIBM.TABLES";
  
  private static final ConnectionProperties _cp = new ConnectionProperties();
  private static final String _sDB_URL = Db2Driver.getUrl(_cp.getHost()+":"+_cp.getPort()+"/"+_cp.getCatalog());
  private static final String _sDB_USER = _cp.getUser();
  private static final String _sDB_PASSWORD = _cp.getPassword();
  private static final String _sDBA_USER = _cp.getDbaUser();
  private static final String _sDBA_PASSWORD = _cp.getDbaPassword();
  
  private Db2Statement _stmtDb2 = null;

  @BeforeClass
  public static void setUpClass()
  {
    try 
    { 
      Db2DataSource dsDb2 = new Db2DataSource();
      dsDb2.setUrl(_sDB_URL);
      dsDb2.setUser(_sDBA_USER);
      dsDb2.setPassword(_sDBA_PASSWORD);
      Db2Connection connDb2 = (Db2Connection)dsDb2.getConnection();
      connDb2.setReadOnly(false);
      /* drop and create the test database */
      new TestSqlDatabase(connDb2,_sDB_USER);
      new TestDb2Database(connDb2,_sDB_USER);
      connDb2.close();
    }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
  } /* setUpClass */
  
  @Before
  public void setUp()
  {
    try 
    { 
      Db2DataSource dsDb2 = new Db2DataSource();
      dsDb2.setUrl(_sDB_URL);
      dsDb2.setUser(_sDB_USER);
      dsDb2.setPassword(_sDB_PASSWORD);
      Db2Connection connDb2 = (Db2Connection)dsDb2.getConnection();
      connDb2.setAutoCommit(false);
      _stmtDb2 = (Db2Statement)connDb2.createStatement();
      setStatement(_stmtDb2);
    }
    catch(SQLException se) { fail(se.getClass().getName()+": "+se.getMessage()); }
  } /* setUp */

  @Test
  public void testClass()
  {
    assertEquals("Wrong statement class!", Db2Statement.class, _stmtDb2.getClass());
  } /* testClass */

  @Test
  @Override
  public void testExecuteUpdate()
  {
    enter();
    try 
    {
      _stmtDb2.getConnection().setAutoCommit(true);
      try { _stmtDb2.executeUpdate(_sSQL_CLEAN); }
      catch(SQLException se) { }
      _stmtDb2.executeUpdate(_sSQL_DDL); 
    }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
  } /* testExecuteUpdate */
  
  @Test
  @Override
  public void testExecuteUpdate_String_int()
  {
    enter();
    /***
    try { _stmtDb2.executeUpdate(_sSQL_CLEAN); }
    catch(SQLException se) {}
    try { _stmtDb2.executeUpdate(_sSQL_DDL, Statement.NO_GENERATED_KEYS); }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
    ***/
  } /* testExecuteUpdate_String_int */
  
  @Test
  @Override
  public void testExecuteUpdate_String_AInt()
  {
    enter();
    /***
    try { _stmtDb2.executeUpdate(_sSQL_CLEAN); }
    catch(SQLException se) {}
    try { _stmtDb2.executeUpdate(_sSQL_DDL, new int[] {1}); }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
    ***/
  } /* testExecuteUpdate_String_AInt */
  
  @Test
  @Override
  public void testExecuteUpdate_String_AString()
  {
    enter();
    /***
    try { _stmtDb2.executeUpdate(_sSQL_CLEAN); }
    catch(SQLException se) {}
    try { _stmtDb2.executeUpdate(_sSQL_DDL, new String[]{"COL_A"}); }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
    ***/
  } /* testExecuteUpdate */

  @Test
  @Override
  public void testGetGeneratedKeys()
  {
    enter();
    try 
    { 
      ResultSet rs = _stmtDb2.getGeneratedKeys();
      while (rs.next())
      {
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++)
        {
          if (i > 0)
            System.out.print(", ");
          System.out.print(rs.getString(i+1));
        }
        System.out.println();
      }
      rs.close();
    }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLException se) { System.out.println(EU.getExceptionMessage(se)); }
  } /* testGetGeneratedKeys */
  
  @Test
  @Override
  public void testExecute_String_AInt()
  {
    enter();
    /***
    try { _stmtDb2.execute(_sSQL_QUERY, new int[] {1}); }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
    ***/
  } /* testExecute_String_AInt */
  
  @Test
  @Override
  public void testExecute_String_AString()
  {
    enter();
    /***
    try { _stmtDb2.execute(_sSQL_QUERY, new String[]{"COL_A"}); }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
    ***/
  } /* testExecute_String_AString */
  
  @Test
  public void testExecuteQuery()
  {
    enter();
    try { _stmtDb2.executeQuery("SELECT LENGTH(TABLE_NAME) FROM SYSIBM.TABLES"); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
  } /* testExecuteQuery */
  
  @Test
  public void testExecute()
  {
    enter();
    try { _stmtDb2.execute(_sSQL_QUERY); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
  } /* testExecute */
  
  @Test
  public void testExecute_String_int()
  {
    enter();
    try { _stmtDb2.execute(_sSQL_QUERY, Statement.NO_GENERATED_KEYS); }
    catch(SQLFeatureNotSupportedException sfnse) { System.out.println(EU.getExceptionMessage(sfnse)); }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
  } /* testExecute_String_int */
  
  @Test
  public void testExecuteSelectSizes()
  {
    StringBuilder sbSql = new StringBuilder("SELECT COUNT(*) AS RECORDS");
    for (int iColumn = 0; iColumn < TestSqlDatabase._listCdSimple.size(); iColumn++)
    {
      TestColumnDefinition tcd = TestSqlDatabase._listCdSimple.get(iColumn);
      if (tcd.getType().startsWith("BLOB") ||
        tcd.getType().startsWith("CLOB") ||
        tcd.getType().startsWith("NCLOB"))
      {
        sbSql.append(",\r\n  SUM(OCTET_LENGTH(");
        sbSql.append(SqlLiterals.formatId(tcd.getName()));
        sbSql.append(")) AS ");
        sbSql.append(SqlLiterals.formatId(tcd.getName()+"_SIZE"));
      }
    }
    sbSql.append("\r\nFROM ");
    sbSql.append(TestSqlDatabase.getQualifiedSimpleTable().format());
    try
    {
      ResultSet rs = _stmtDb2.executeQuery(sbSql.toString());
      ResultSetMetaData rsmd = rs.getMetaData();
      while(rs.next())
      {
        for (int iColumn = 0; iColumn < rsmd.getColumnCount(); iColumn++)
        {
          String sColumnName = rsmd.getColumnLabel(iColumn+1);
          long lValue = rs.getLong(iColumn+1);
          System.out.println(sColumnName+": "+String.valueOf(lValue));
        }
      }
      rs.close();
    }
    catch(SQLException se) { System.out.println(EU.getExceptionMessage(se)); }
  } /* testExecuteSelectSize */

  @Test
  public void testDropTableCascade()
  {
    enter();
    try 
    {
      _stmtDb2.executeUpdate("DROP TABLE "+TestSqlDatabase.getQualifiedSimpleTable().format()+" CASCADE"); 
      // restore the database
      tearDown();
      setUpClass();
      setUp();
    }
    catch(SQLTimeoutException ste) { fail(EU.getExceptionMessage(ste)); }
    catch(SQLException se) { fail(EU.getExceptionMessage(se)); }
  }
  
}  /* class Db2StatementTester */
