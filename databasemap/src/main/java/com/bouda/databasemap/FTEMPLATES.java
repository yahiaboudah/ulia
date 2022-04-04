package com.bouda.databasemap;

import static com.bouda.underscore.UnderScore._;
import java.util.Map;

public class FTEMPLATES {

    public static final Map<String, ?> SIMPLE_ID = _(
            "datatype", StorageClass.INTEGER,
            "isPrimary", true
    );

    public static final Map<String, ?> AUTO_ID = _(
            "datatype", StorageClass.INTEGER,
            "isPrimary", true,
            "autoIncrement", true
    );

    public static final Map<String, ?> TITLE = _(
            "datatype", StorageClass.TEXT,
            "unique", true,
            "notNull", true
    );
    public static final Map<String, ?> DESC = _(
            "datatype", StorageClass.TEXT,
            "unique", true,
            "default", ""
    );
}
