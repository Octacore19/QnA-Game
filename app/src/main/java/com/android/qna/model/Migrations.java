package com.android.qna.model;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class Migrations implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("Questions")
                    .addField("id", Integer.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                    .addField("question", String.class)
                    .addField("answer", String.class)
                    .addField("options", Options.class);
            oldVersion++;
        }
    }
}