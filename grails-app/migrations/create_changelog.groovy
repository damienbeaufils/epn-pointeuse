databaseChangeLog = {

	changeSet(author: "generated", id: "1414012990009-1") {
		createTable(tableName: "signed_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "signed_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "full_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}
}
