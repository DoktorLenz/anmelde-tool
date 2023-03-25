# Anmeldetool

## Installation / first start

1. As a dependency you need a postgresql database
2. Create a Spring-boot run-config and provide the following keys with values as ``Program Arguments``:
    - ``--MAIL_HOST=<smtp-address>``
    - ``--MAIL_PORT=<smtp-port>``
    - ``--MAIL_USERNAME=<max.musterman@mail.com>``
    - ``--MAIL_PASSWORD=<token>``
3. Optional you can change the connection for the database via the ``Program Arguments`` but defaults are set
   in ``application.yml``