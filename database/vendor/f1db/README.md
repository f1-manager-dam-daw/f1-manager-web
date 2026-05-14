# F1DB SQL dataset

Source: https://github.com/f1db/f1db/releases/tag/v2026.4.2

Downloaded asset:

```text
f1db-sql-mysql-single-inserts.zip
```

Why this file:

- MySQL/MariaDB compatible, suitable for AWS RDS MariaDB/MySQL.
- Compressed size is around 4.6 MB, acceptable for the repository.
- Extracted SQL is around 34 MB, so the extracted `.sql` file is not committed to avoid repository bloat.

## Integrity

The expected SHA-256 checksum is included in `checksums_sha256.txt`.

Current checksum:

```text
7eea2f74ea4f0dedb84edf67a1bb34d9e997946e6967be1fe1d2f0a48d901931  f1db-sql-mysql-single-inserts.zip
```

Verify with:

```bash
sha256sum f1db-sql-mysql-single-inserts.zip
```

## Import into AWS RDS / MariaDB / MySQL

Unzip:

```bash
unzip database/vendor/f1db/f1db-sql-mysql-single-inserts.zip -d /tmp/f1db-sql
```

Import:

```bash
mysql \
  -h <aws-rds-endpoint> \
  -P 3306 \
  -u f1_app \
  -p \
  f1_manager < /tmp/f1db-sql/f1db-sql-mysql-single-inserts.sql
```

If importing with an admin user first, create the application user separately and grant only the required permissions.

## Useful tables/views for the project

F1DB uses singular table names and detailed domain objects. The most relevant ones for our project are:

- `driver`
- `constructor`
- `race`
- `race_data`
- `race_result` view
- `country`
- `circuit`
- `grand_prix`

Our Java app can either use these tables directly or create simpler project-specific views.

## License / attribution

F1DB is published under Creative Commons Attribution 4.0.

Attribution should be included in the README/presentation if this dataset is used:

> Formula 1 data provided by F1DB — https://github.com/f1db/f1db — CC BY 4.0.
