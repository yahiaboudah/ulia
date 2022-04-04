package com.bouda.databasemap;


import static com.bouda.databasemap.DatabaseMap.CONSTR;
import static com.bouda.databasemap.DatabaseMap.FIELDS;
import static com.bouda.databasemap.DatabaseMap.TABLES;
import static com.bouda.underscore.UnderScore._;
import static com.bouda.underscore.UnderScore.__;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleUnitTest {

    @Test
    public void main(){
        Map<String, Map<String, Map<String, ?>>> structMap = _(
                "products", _(
                        "id", _(
                                "type", "PRIMARY",
                                "datatype", "INTEGER"
                        ),
                        "price", _(
                                "type", "SECONDARY",
                                "datatype", "FLOAT"
                        ),
                        "desc", _(
                                "type", "SECONDARY",
                                "datatype", "TEXT"
                        )
                )
        );

        List<String> queries = new ArrayList<>();

        structMap.forEach((tableName, tableFields) -> {
            queries.add(String.format(
                    "CREATE TABLE %s (%s);",
                    tableName, tableFields.entrySet().stream().map((e) -> String.format(
                            "%s %s%s",
                            e.getKey(), e.getValue().get("datatype"),
                            (e.getValue().get("type") == " PRIMARY")? "PRIMARY":""
                    )).collect(Collectors.joining(", "))
            ));
        });

        queries.forEach(System.out::println);
    }

    @Test
    public void testInterface(){
    }

    @Test
    public void mapTheMap(){
        Map<String, String> map = _(
                "hello", "greeting",
                "piss off", "repulsion",
                "get out", "repulsion",
                "welcome", "greeting"
        );

        String mapped = map.entrySet().stream().map((e) -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(",\n", "[\n", "\n]"));

        System.out.println(mapped);
    }

    @Test
    public void testMe() throws JSONException {

        DatabaseMap myDb = new DatabaseMap(_(
                TABLES, _(

                        // [BLOB]
                        "BLOB", _(
                                FIELDS, _(
                                        "blob_id", FTEMPLATES.AUTO_ID,
                                        "title", FTEMPLATES.TITLE,
                                        "desc", FTEMPLATES.DESC
                                )
                        ),

                        // [PLOB]
                        "PLOB", _(
                                FIELDS, _(
                                        "plob_id", FTEMPLATES.AUTO_ID,
                                        "title", FTEMPLATES.TITLE,
                                        "desc", FTEMPLATES.DESC,
                                        "container_node_id", FTEMPLATES.SIMPLE_ID
                                ),
                                CONSTR, _(
                                        CONSTRAINTS.FOREGINKEY, __("container_node_id", "node_id", "NODE")
                                )
                        ),

                        // [NODE]
                        "NODE", _(
                                FIELDS, _(
                                        "node_id", FTEMPLATES.AUTO_ID,
                                        "parent_node_id", FTEMPLATES.SIMPLE_ID,
                                        "title", FTEMPLATES.TITLE,
                                        "desc", FTEMPLATES.DESC
                                ),
                                CONSTR, _(
                                        CONSTRAINTS.FOREGINKEY, __("parent_node_id", "node_id", "NODE")
                                )
                        ),

                        // [UNODE]
                        "UNODE", _(
                                FIELDS, _(
                                        "unode_id", FTEMPLATES.AUTO_ID,
                                        "title", FTEMPLATES.TITLE,
                                        "desc", FTEMPLATES.DESC,
                                        "parent_node_id",  FTEMPLATES.SIMPLE_ID
                                ),
                                CONSTR, _(
                                        CONSTRAINTS.FOREGINKEY, __("parent_node_id", "node_id", "NODE")
                                )
                        ),

                        // [ACTION]
                        "ACTION", _(
                                FIELDS, _(
                                        "action_id", FTEMPLATES.AUTO_ID,
                                        "title", FTEMPLATES.TITLE,
                                        "desc", FTEMPLATES.DESC,
                                        "parent_unode_id", FTEMPLATES.SIMPLE_ID
                                ),
                                CONSTR, _(
                                        CONSTRAINTS.FOREGINKEY, __("paren_unode_id", "unode_id", "UNODE")
                                )
                        ),

                        // [TACTION]
                        "TACTION", _(
                                FIELDS, _(
                                        "taction_id", FTEMPLATES.AUTO_ID,
                                        "title", FTEMPLATES.TITLE,
                                        "desc", FTEMPLATES.DESC,
                                        "parent_unode_id", FTEMPLATES.SIMPLE_ID
                                ),
                                CONSTR, _(
                                        CONSTRAINTS.FOREGINKEY, __("parent_unode_id", "unode_id", "UNODE")
                                )
                        )
                )
        ));

        System.out.println(new JSONObject(myDb.getMap()).toString(4));
    }
}