package com.android.qna.model;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migrations implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema gameSchema = schema.get("Game");
            gameSchema
                    .addField("id", String.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED);
            oldVersion++;
        }
    }
}