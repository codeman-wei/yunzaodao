/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/3/28 11:05:56                           */
/*==============================================================*/


drop table if exists button;

drop table if exists course;

drop table if exists dictionary_detail;

drop table if exists dictionary_type;

drop table if exists menu;

drop table if exists role;

drop table if exists sign_on;

drop table if exists user;

/*==============================================================*/
/* Table: button                                                */
/*==============================================================*/
create table button
(
   id                   bigint not null,
   button_chinese       varchar(10) not null,
   button_english       varchar(10) not null,
   detail               varchar(50) not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: course                                                */
/*==============================================================*/
create table course
(
   id                   bigint not null,
   s_id                 bigint not null,
   t_id                 bigint not null,
   course_id            varchar(10) not null,
   course_name          varchar(20) not null,
   school               varchar(20) not null,
   college              varchar(20) not null,
   class_time           date not null,
   teacher_name         varchar(20) not null,
   is_over              tinyint not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: dictionary_detail                                     */
/*==============================================================*/
create table dictionary_detail
(
   id                   bigint not null,
   order_id             int not null,
   value                varchar(20) not null,
   is_default           tinyint not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: dictionary_type                                       */
/*==============================================================*/
create table dictionary_type
(
   id                   bigint not null,
   detail_id            bigint not null,
   dictionary_name      varchar(20) not null,
   detail               varchar(50) not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: menu                                                  */
/*==============================================================*/
create table menu
(
   id                   bigint not null,
   icon                 blob not null,
   link                 varchar(100) not null,
   parent_menu          int not null,
   order_id             int not null,
   is_menu              tinyint not null,
   is_page              tinyint not null,
   button               varchar(50) not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   bigint not null,
   b_id                 bigint,
   m_id                 bigint,
   role_name            varchar(20) not null,
   role_detail          varchar(50) not null,
   menu_permissions     varchar(50) not null,
   button_permissions   varchar(50) not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: sign_on                                               */
/*==============================================================*/
create table sign_on
(
   id                   bigint not null,
   c_id                 bigint,
   start_date           datetime not null,
   end_date             datetime not null,
   experience           int not null,
   creater              int not null,
   creation_date        datetime not null,
   modification_date    datetime not null,
   modifier             int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null,
   user                 varchar(20) not null,
   pass_word            varchar(20) not null,
   user_name            varchar(10) not null,
   sex                  bit not null,
   u_id                 varchar(20) not null,
   school               varchar(20) not null,
   college              varchar(20) not null,
   phone_number         varchar(20) not null,
   profile_picture      blob not null,
   role_id              bigint,
   creater              int not null,
   creation_date        datetime not null,
   modification_date1   datetime not null,
   modifier             int not null,
   primary key (id)
);

alter table course add constraint FK_Reference_3 foreign key (s_id)
      references user (id) on delete restrict on update restrict;

alter table course add constraint FK_Reference_4 foreign key (t_id)
      references user (id) on delete restrict on update restrict;

alter table dictionary_type add constraint FK_Reference_2 foreign key (detail_id)
      references dictionary_detail (id) on delete restrict on update restrict;

alter table role add constraint FK_Reference_6 foreign key (b_id)
      references button (id) on delete restrict on update restrict;

alter table role add constraint FK_Reference_7 foreign key (m_id)
      references menu (id) on delete restrict on update restrict;

alter table sign_on add constraint FK_Reference_1 foreign key (c_id)
      references course (id) on delete restrict on update restrict;

alter table user add constraint FK_Reference_5 foreign key (role_id)
      references role (id) on delete restrict on update restrict;

