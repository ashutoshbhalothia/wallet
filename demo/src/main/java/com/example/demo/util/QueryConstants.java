package com.example.demo.util;

public class QueryConstants {

    private QueryConstants(){}

    public static final String selectQuery = "SELECT <COLUMNS> FROM <TABLE> AS <T>";
    public static final String join = "JOIN <TABLE> AS <T> ";
    public static final String where = "WHERE ";
    public static final String AND = "A ND ";
    public static final String OR = " OR ";
    public static final String ON = " ON <C1> = <C2> ";

    public static final String allColumns = "*";
    public static final String tableName = "<TABLE>";
    public static final String tableAlias = "<T>";
    public static final String colums = "<COLUMNS>";
    public static final String firstColumn = "<C1>";
    public static final String secondColumn = "<C2>";
}
