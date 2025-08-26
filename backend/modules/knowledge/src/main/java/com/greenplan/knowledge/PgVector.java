package com.greenplan.knowledge;

import java.sql.*;

public record PgVector(float[] data) implements SQLData {
    @Override
    public String getSQLTypeName() {
        return "vector";
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) {
    }

    @Override
    public void writeSQL(SQLOutput stream) {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < data.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(data[i]);
        }
        return sb.append(']').toString();
    }
}