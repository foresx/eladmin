-- init user managerment datastructure

CREATE TABLE dept
(
    id         bigserial    NOT NULL,
    name       varchar(255) NOT NULL,
    code       varchar(50)  NOT NULL,
    pid        bigint NOT NULL,
    enabled    boolean NOT NULL DEFAULT TRUE,
    type       varchar(50),
    create_time timestamp,
--    updated_at timestamp,
--    created_by varchar(255),
--    updated_by varchar(255),
    CONSTRAINT pk_dept_id PRIMARY KEY (id),
    CONSTRAINT uk_dept_code UNIQUE (code)
);

CREATE TABLE role
(
    id         bigserial    NOT NULL,
    name       varchar(255) NOT NULL,
    code       varchar(50),
    level      integer,
    remark     varchar(255),
    data_scope     varchar(255),
    create_time timestamp,
--    updated_at timestamp,
--    created_by varchar(255),
--    updated_by varchar(255),
    CONSTRAINT pk_role_id PRIMARY KEY (id),
    CONSTRAINT uk_role_code UNIQUE (code)
);

CREATE TABLE permission
(
    id        bigserial    NOT NULL,
    name      varchar(255),
    code      varchar(255),
    description      varchar(255),
    type      varchar(255) NOT NULL,
    pid        bigint NOT NULL,
    create_time timestamp,
--    updated_at timestamp,
--    created_by varchar(255),
--    updated_by varchar(255),
    CONSTRAINT pk_permission_id PRIMARY KEY (id)
);

CREATE TABLE roles_permissions
(
    role_id    bigint    NOT NULL,
    permission_id    bigint    NOT NULL,
    CONSTRAINT pk_roles_permissions_id PRIMARY KEY (role_id, permission_id)
);

ALTER table roles_permissions
  ADD CONSTRAINT fk_roles_permissions_role FOREIGN KEY (role_id) REFERENCES role (id);
ALTER table roles_permissions
  ADD CONSTRAINT fk_roles_permissions_permission FOREIGN KEY (permission_id) REFERENCES permission (id);

-- for deptment private role
CREATE TABLE roles_depts
(
    role_id    bigint    NOT NULL,
    dept_id    bigint    NOT NULL,
    CONSTRAINT pk_roles_depts_id PRIMARY KEY (role_id, dept_id)
);

ALTER table roles_depts
  ADD CONSTRAINT fk_roles_depts_role FOREIGN KEY (role_id) REFERENCES role (id);
ALTER table roles_depts
  ADD CONSTRAINT fk_roles_depts_dept FOREIGN KEY (dept_id) REFERENCES dept (id);

CREATE TABLE "sys_user"
(
    id         bigserial    NOT NULL,
    username       varchar(255) NOT NULL,
    password       varchar(255) NOT NULL,
    email       varchar(255) NOT NULL,
    phone       varchar(255),
    enabled       boolean DEFAULT true,
--    code       varchar(50)  NOT NULL,
    last_password_reset_time timestamp,
    create_time timestamp,
--    updated_at timestamp,
--    created_by varchar(255),
--    updated_by varchar(255),
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT uk_user_email UNIQUE (email)
);

CREATE TABLE users_roles
(
    user_id    bigint    NOT NULL,
    role_id    bigint    NOT NULL,
    CONSTRAINT pk_users_roles_id PRIMARY KEY (user_id, role_id)
);

ALTER table users_roles
  ADD CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES role (id);
ALTER table users_roles
  ADD CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES "sys_user" (id);

CREATE TABLE users_depts
(
    user_id    bigint    NOT NULL,
    dept_id    bigint    NOT NULL,
    CONSTRAINT pk_users_depts_id PRIMARY KEY (user_id, dept_id)
);

ALTER table users_depts
  ADD CONSTRAINT fk_users_depts_dept FOREIGN KEY (dept_id) REFERENCES dept (id);
ALTER table users_depts
  ADD CONSTRAINT fk_users_depts_user FOREIGN KEY (user_id) REFERENCES "sys_user" (id);

CREATE TABLE log
(
    id        bigserial    NOT NULL,
    description varchar(255),
    log_type      varchar(255),
    method      varchar(255),
    request_ip      varchar(255),
    username      varchar(255),
    address      varchar(255),
    browser      varchar(255),
    time      bigint,
    exception_detail text,
    params text,
    create_time timestamp,
--    updated_at timestamp,
--    created_by varchar(255),
--    updated_by varchar(255),
    CONSTRAINT pk_log_id PRIMARY KEY (id)
);