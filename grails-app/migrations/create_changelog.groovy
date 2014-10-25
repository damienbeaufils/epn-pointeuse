databaseChangeLog = {

	changeSet(author: "generated", id: "1414252618303-1") {
		createTable(tableName: "new_user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "new_userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "birth_year", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "city", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "male", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "varchar(14)") {
				constraints(nullable: "false")
			}

			column(name: "street", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(5)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "generated", id: "1414252618303-2") {
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
