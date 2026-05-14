# AWS RDS Plan

The project database will be hosted on AWS RDS instead of a home server/Tailscale setup.

## Recommended engine

Amazon RDS for MariaDB/MySQL.

## Planned database

```text
database: f1_manager
application user: f1_app
```

## Security rules

- Do not use the RDS admin user from the Java application.
- Do not commit passwords or local endpoints.
- Use environment variables or local ignored configuration files.
- Restrict the RDS Security Group to known development/presentation IPs where possible.

## Java JDBC URL template

```text
jdbc:mysql://<aws-rds-endpoint>:3306/f1_manager
```

## Documentation still needed

- RDS creation screenshots/steps.
- Security Group configuration.
- Database schema import instructions.
- Environment variable names for the Java app.
