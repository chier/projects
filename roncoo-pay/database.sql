/*==============================================================*/
/* dbms name:      mysql 5.0                                    */
/* created on:     2016-6-29 18:28:57   www.roncoo.com          */
/*==============================================================*/


drop table if exists rp_account;

drop table if exists rp_account_history;

drop table if exists rp_pay_product;

drop table if exists rp_pay_way;

drop table if exists rp_sett_daily_collect;

drop table if exists rp_sett_record;

drop table if exists rp_sett_record_annex;

drop table if exists rp_user_bank_account;

drop table if exists rp_user_info;

drop table if exists rp_user_pay_config;

drop table if exists rp_user_pay_info;

drop table if exists rp_account_check_batch;

drop table if exists rp_account_check_mistake;

drop table if exists rp_account_check_mistake_scratch_pool;

drop table if exists rp_notify_record;

drop table if exists rp_notify_record_log;

drop table if exists rp_refund_record;

drop table if exists rp_trade_payment_order;

drop table if exists rp_trade_payment_record;

drop table if exists seq_table;

/*==============================================================*/
/* table: rp_account                                            */
/*==============================================================*/
create table rp_account
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   account_no           varchar(50) not null,
   balance              decimal(20,6) not null,
   unbalance            decimal(20,6) not null,
   security_money       decimal(20,6) not null,
   status               varchar(36) not null,
   total_income         decimal(20,6) not null,
   total_expend         decimal(20,6) not null,
   today_income         decimal(20,6) not null,
   today_expend         decimal(20,6) not null,
   account_type         varchar(50) not null,
   sett_amount          decimal(20,6) not null,
   user_no              varchar(50),
   primary key (id)
);

alter table rp_account comment '�ʽ��˻���';

/*==============================================================*/
/* table: rp_account_history                                    */
/*==============================================================*/
create table rp_account_history
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   account_no           varchar(50) not null,
   amount               decimal(20,6) not null,
   balance              decimal(20,6) not null,
   fund_direction       varchar(36) not null,
   is_allow_sett        varchar(36) not null,
   is_complete_sett     varchar(36) not null,
   request_no           varchar(36) not null,
   bank_trx_no          varchar(30),
   trx_type             varchar(36) not null,
   risk_day             int,
   user_no              varchar(50),
   primary key (id)
);

alter table rp_account_history comment '�ʽ��˻���ˮ��';

/*==============================================================*/
/* table: rp_pay_product                                        */
/*==============================================================*/
create table rp_pay_product
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   status               varchar(36) not null,
   product_code         varchar(50) not null comment '֧����Ʒ���',
   product_name         varchar(200) not null comment '֧����Ʒ����',
   audit_status         varchar(45),
   primary key (id)
);

alter table rp_pay_product comment '֧����Ʒ��';

/*==============================================================*/
/* table: rp_pay_way                                            */
/*==============================================================*/
create table rp_pay_way
(
   id                   varchar(50) not null comment 'id',
   version              bigint not null default 0 comment 'version',
   create_time          datetime not null comment '����ʱ��',
   edit_time            datetime comment '�޸�ʱ��',
   pay_way_code         varchar(50) not null comment '֧����ʽ���',
   pay_way_name         varchar(100) not null comment '֧����ʽ����',
   pay_type_code        varchar(50) not null comment '֧�����ͱ��',
   pay_type_name        varchar(100) not null comment '֧����������',
   pay_product_code     varchar(50) comment '֧����Ʒ���',
   status               varchar(36) not null comment '״̬(100:����״̬,101������״̬)',
   sorts                int default 1000 comment '����(��������,Ĭ��ֵ1000)',
   pay_rate             double not null comment '�̻�֧������',
   primary key (id)
);

alter table rp_pay_way comment '֧����ʽ';

/*==============================================================*/
/* table: rp_sett_daily_collect                                 */
/*==============================================================*/
create table rp_sett_daily_collect
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '�汾��',
   create_time          datetime not null comment '����ʱ��',
   edit_time            datetime not null comment '�޸�ʱ��',
   account_no           varchar(20) not null comment '�˻����',
   user_name            varchar(200) comment '�û�����',
   collect_date         date not null comment '��������',
   collect_type         varchar(50) not null comment '��������(�ο�ö��:settdailycollecttypeenum)',
   total_amount         decimal(24,10) not null comment '�����ܽ��',
   total_count          int not null comment '�����ܱ���',
   sett_status          varchar(50) not null comment '����״̬(�ο�ö��:settdailycollectstatusenum)',
   remark               varchar(300) comment '��ע',
   risk_day             int comment '����Ԥ��������',
   primary key (id)
);

alter table rp_sett_daily_collect comment 'ÿ�մ��������';

/*==============================================================*/
/* table: rp_sett_record                                        */
/*==============================================================*/
create table rp_sett_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '�汾��',
   create_time          datetime not null comment '����ʱ��',
   edit_time            datetime not null comment '�޸�ʱ��',
   sett_mode            varchar(50) comment '���㷢��ʽ(�ο�settmodetypeenum)',
   account_no           varchar(20) not null comment '�˻����',
   user_no              varchar(20) comment '�û����',
   user_name            varchar(200) comment '�û�����',
   user_type            varchar(50) comment '�û�����',
   sett_date            date comment '��������',
   bank_code            varchar(20) comment '���б���',
   bank_name            varchar(100) comment '��������',
   bank_account_name    varchar(60) comment '������',
   bank_account_no      varchar(20) comment '�����˻�',
   bank_account_type    varchar(50) comment '�����˻�',
   country              varchar(200) comment '���������ڹ���',
   province             varchar(50) comment '����������ʡ��',
   city                 varchar(50) comment '���������ڳ���',
   areas                varchar(50) comment '������������',
   bank_account_address varchar(300) comment '������ȫ��',
   mobile_no            varchar(20) comment '�տ����ֻ���',
   sett_amount          decimal(24,10) comment '������',
   sett_fee             decimal(16,6) comment '����������',
   remit_amount         decimal(16,2) comment '��������',
   sett_status          varchar(50) comment '����״̬(�ο�ö��:settrecordstatusenum)',
   remit_confirm_time   datetime comment '���ȷ��ʱ��',
   remark               varchar(200) comment '����',
   remit_remark         varchar(200) comment '��ע',
   operator_loginname   varchar(50) comment '����Ա��¼��',
   operator_realname    varchar(50) comment '����Ա����',
   primary key (id)
);

alter table rp_sett_record comment '�����¼';

/*==============================================================*/
/* table: rp_sett_record_annex                                  */
/*==============================================================*/
create table rp_sett_record_annex
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   is_delete            varchar(36) not null,
   annex_name           varchar(200),
   annex_address        varchar(500) not null,
   settlement_id        varchar(50) not null,
   primary key (id)
);

/*==============================================================*/
/* table: rp_user_bank_account                                  */
/*==============================================================*/
create table rp_user_bank_account
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   user_no              varchar(50) not null,
   bank_name            varchar(200) not null,
   bank_code            varchar(50) not null,
   bank_account_name    varchar(100) not null,
   bank_account_no      varchar(36) not null,
   card_type            varchar(36) not null,
   card_no              varchar(36) not null,
   mobile_no            varchar(50) not null,
   is_default           varchar(36),
   province             varchar(20),
   city                 varchar(20),
   areas                varchar(20),
   street               varchar(300),
   bank_account_type    varchar(36) not null,
   primary key (id)
);

alter table rp_user_bank_account comment '�û������˻���';

/*==============================================================*/
/* table: rp_user_info                                          */
/*==============================================================*/
create table rp_user_info
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   status               varchar(36) not null,
   user_no              varchar(50),
   user_name            varchar(100),
   account_no           varchar(50) not null,
   primary key (id),
   unique key ak_key_2 (account_no)
);

alter table rp_user_info comment '�ñ���������û��Ļ�����Ϣ';

/*==============================================================*/
/* table: rp_user_pay_config                                    */
/*==============================================================*/
create table rp_user_pay_config
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   audit_status         varchar(45),
   is_auto_sett         varchar(36) not null default 'no',
   product_code         varchar(50) not null comment '֧����Ʒ���',
   product_name         varchar(200) not null comment '֧����Ʒ����',
   user_no              varchar(50),
   user_name            varchar(100),
   risk_day             int,
   pay_key              varchar(50),
   fund_into_type       varchar(50),
   pay_secret           varchar(50),
   primary key (id)
);

alter table rp_user_pay_config comment '֧�����ñ�';

/*==============================================================*/
/* table: rp_user_pay_info                                      */
/*==============================================================*/
create table rp_user_pay_info
(
   id_                  varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   app_id               varchar(50) not null,
   app_sectet           varchar(100),
   merchant_id          varchar(50),
   app_type             varchar(50),
   user_no              varchar(50),
   user_name            varchar(100),
   partner_key          varchar(100),
   pay_way_code         varchar(50) not null comment '֧����ʽ���',
   pay_way_name         varchar(100) not null comment '֧����ʽ����',
   primary key (id_)
);

alter table rp_user_pay_info comment '�ñ���������û���ͨ�ĵ�����֧����Ϣ';


create table rp_account_check_batch
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   status               varchar(30) not null,
   remark               varchar(500),
   batch_no             varchar(30) not null,
   bill_date            date not null,
   bill_type            varchar(30),
   handle_status        varchar(10),
   bank_type            varchar(30),
   mistake_count        int(8),
   unhandle_mistake_count int(8),
   trade_count          int(8),
   bank_trade_count     int(8),
   trade_amount         decimal(20,6),
   bank_trade_amount    decimal(20,6),
   refund_amount        decimal(20,6),
   bank_refund_amount   decimal(20,6),
   bank_fee             decimal(20,6),
   org_check_file_path  varchar(500),
   release_check_file_path varchar(500),
   release_status       varchar(15),
   check_fail_msg       varchar(300),
   bank_err_msg         varchar(300),
   primary key (id)
);

alter table rp_account_check_batch comment '�������α� rp_account_check_batch';

create table rp_account_check_mistake
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   status               varchar(30),
   remark               varchar(500),
   account_check_batch_no varchar(50) not null,
   bill_date            date not null,
   bank_type            varchar(30) not null,
   order_time           datetime,
   merchant_name        varchar(100),
   merchant_no          varchar(50),
   order_no             varchar(40),
   trade_time           datetime,
   trx_no               varchar(20),
   order_amount         decimal(20,6),
   refund_amount        decimal(20,6),
   trade_status         varchar(30),
   fee                  decimal(20,6),
   bank_trade_time      datetime,
   bank_order_no        varchar(40),
   bank_trx_no          varchar(40),
   bank_trade_status    varchar(30),
   bank_amount          decimal(20,6),
   bank_refund_amount   decimal(20,6),
   bank_fee             decimal(20,6),
   err_type             varchar(30) not null,
   handle_status        varchar(10) not null,
   handle_value         varchar(1000),
   handle_remark        varchar(1000),
   operator_name        varchar(100),
   operator_account_no  varchar(50),
   primary key (id)
);

alter table rp_account_check_mistake comment '���˲��� rp_account_check_mistake';

create table rp_account_check_mistake_scratch_pool
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   product_name         varchar(50) comment '��Ʒ����',
   merchant_order_no    varchar(30) not null comment '�̻�������',
   trx_no               char(20) not null comment '֧����ˮ��',
   bank_order_no        char(20) comment '���ж�����',
   bank_trx_no          varchar(30) comment '������ˮ��',
   order_amount         decimal(20,6) default 0 comment '�������',
   plat_income          decimal(20,6) comment 'ƽ̨����',
   fee_rate             decimal(20,6) comment '����',
   plat_cost            decimal(20,6) comment 'ƽ̨�ɱ�',
   plat_profit          decimal(20,6) comment 'ƽ̨����',
   status               varchar(30) comment '״̬(�ο�ö��:paymentrecordstatusenum)',
   pay_way_code         varchar(50) comment '֧��ͨ�����',
   pay_way_name         varchar(100) comment '֧��ͨ������',
   pay_success_time     datetime comment '֧���ɹ�ʱ��',
   complete_time        datetime comment '���ʱ��',
   is_refund            varchar(30) default '101' comment '�Ƿ��˿�(100:��,101:��,Ĭ��ֵΪ:101)',
   refund_times         smallint default 0 comment '�˿����(Ĭ��ֵΪ:0)',
   success_refund_amount decimal(20,6) comment '�ɹ��˿��ܽ��',
   remark               varchar(500) comment '��ע',
   batch_no             varchar(50),
   bill_date            datetime
);

alter table rp_account_check_mistake_scratch_pool comment '����ݴ��';

create table rp_notify_record
(
   id                   varchar(50) not null,
   version              int not null,
   create_time          datetime not null,
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   notify_times         int not null,
   limit_notify_times   int not null,
   url                  varchar(2000) not null,
   merchant_order_no    varchar(50) not null,
   merchant_no          varchar(50) not null,
   status               varchar(50) not null comment '100:�ɹ� 101:ʧ��',
   notify_type          varchar(30) comment '֪ͨ����',
   primary key (id),
   key ak_key_2 (merchant_order_no)
);

alter table rp_notify_record comment '֪ͨ��¼�� rp_notify_record';

create table rp_notify_record_log
(
   id                   varchar(50) not null,
   version              int not null,
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   create_time          datetime not null,
   notify_id            varchar(50) not null,
   request              varchar(2000) not null,
   response             varchar(2000) not null,
   merchant_no          varchar(50) not null,
   merchant_order_no    varchar(50) not null comment '�̻�������',
   http_status          varchar(50) not null comment 'http״̬',
   primary key (id)
);

alter table rp_notify_record_log comment '֪ͨ��¼��־�� rp_notify_record_log';

create table rp_refund_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null comment '�汾��',
   create_time          datetime comment '����ʱ��',
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   org_merchant_order_no varchar(50) comment 'ԭ�̻�������',
   org_trx_no           varchar(50) comment 'ԭ֧����ˮ��',
   org_bank_order_no    varchar(50) comment 'ԭ���ж�����',
   org_bank_trx_no      varchar(50) comment 'ԭ������ˮ��',
   merchant_name        varchar(100) comment '�̼�����',
   merchant_no          varchar(100) not null comment '�̼ұ��',
   org_product_name     varchar(60) comment 'ԭ��Ʒ����',
   org_biz_type         varchar(30) comment 'ԭҵ������',
   org_payment_type     varchar(30) comment 'ԭ֧����ʽ����',
   refund_amount        decimal(20,6) comment '�����˿���',
   refund_trx_no        varchar(50) not null comment '�˿���ˮ��',
   refund_order_no      varchar(50) not null comment '�˿����',
   bank_refund_order_no varchar(50) comment '�����˿����',
   bank_refund_trx_no   varchar(30) comment '�����˿���ˮ��',
   result_notify_url    varchar(500) comment '�˿���֪ͨurl',
   refund_status        varchar(30) comment '�˿�״̬',
   refund_from          varchar(30) comment '�˿���Դ���ַ�ƽ̨��',
   refund_way           varchar(30) comment '�˿ʽ',
   refund_request_time  datetime comment '�˿�����ʱ��',
   refund_success_time  datetime comment ' �˿�ɹ�ʱ��',
   refund_complete_time datetime comment '�˿����ʱ��',
   request_apply_user_id varchar(50) comment '�˿�����,�����˵�¼��',
   request_apply_user_name varchar(90) comment '�˿�����,����������',
   refund_reason        varchar(500) comment '�˿�ԭ��',
   remark               varchar(3000) comment '��ע',
   primary key (id),
   unique key ak_key_2 (refund_trx_no)
);

alter table rp_refund_record comment '�˿��¼��';

create table rp_trade_payment_order
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '�汾��',
   create_time          datetime not null comment '����ʱ��',
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   status               varchar(50) comment '״̬(�ο�ö��:orderstatusenum)',
   product_name         varchar(300) comment '��Ʒ����',
   merchant_order_no    varchar(30) not null comment '�̻�������',
   order_amount         decimal(20,6) default 0 comment '�������',
   order_from           varchar(30) comment '������Դ',
   merchant_name        varchar(100) comment '�̼�����',
   merchant_no          varchar(100) not null comment '�̻����',
   order_time           datetime comment '�µ�ʱ��',
   order_date           date comment '�µ�����',
   order_ip             varchar(50) comment '�µ�ip(�ͻ���ip,������ҳ���ȡ)',
   order_referer_url    varchar(100) comment '���ĸ�ҳ�����ӹ�����(�����ڷ�թƭ)',
   return_url           varchar(600) comment 'ҳ��ص�֪ͨurl',
   notify_url           varchar(600) comment '��̨�첽֪ͨurl',
   cancel_reason        varchar(600) comment '��������ԭ��',
   order_period         smallint comment '������Ч��(��λ����)',
   expire_time          datetime comment '����ʱ��',
   pay_way_code         varchar(50) comment '֧����ʽ���',
   pay_way_name         varchar(100) comment '֧����ʽ����',
   remark               varchar(200) comment '֧����ע',
   trx_type             varchar(30) comment '����ҵ������  �����ѡ���ֵ��',
   trx_no               varchar(50) comment '֧����ˮ��',
   pay_type_code        varchar(50) comment '֧�����ͱ��',
   pay_type_name        varchar(100) comment '֧����������',
   fund_into_type       varchar(30) comment '�ʽ���������',
   is_refund            varchar(30) default '101' comment '�Ƿ��˿�(100:��,101:��,Ĭ��ֵΪ:101)',
   refund_times         int default 0 comment '�˿����(Ĭ��ֵΪ:0)',
   success_refund_amount decimal(20,6) comment '�ɹ��˿��ܽ��',
   field1               varchar(500),
   field2               varchar(500),
   field3               varchar(500),
   field4               varchar(500),
   field5               varchar(500),
   primary key (id),
   unique key ak_key_2 (merchant_order_no, merchant_no)
);

alter table rp_trade_payment_order comment 'roncoo pay ����֧�� ֧��������';

create table rp_trade_payment_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '�汾��',
   create_time          datetime comment '����ʱ��',
   status               varchar(30) comment '״̬(�ο�ö��:paymentrecordstatusenum)',
   editor               varchar(100) comment '�޸���',
   creater              varchar(100) comment '������',
   edit_time            datetime comment '����޸�ʱ��',
   product_name         varchar(50) comment '��Ʒ����',
   merchant_order_no    varchar(50) not null comment '�̻�������',
   trx_no               varchar(50) not null comment '֧����ˮ��',
   bank_order_no        varchar(50) comment '���ж�����',
   bank_trx_no          varchar(50) comment '������ˮ��',
   merchant_name        varchar(300) comment '�̼�����',
   merchant_no          varchar(50) not null comment '�̼ұ��',
   payer_user_no        varchar(50) comment '�����˱��',
   payer_name           varchar(60) comment '����������',
   payer_pay_amount     decimal(20,6) default 0 comment '���֧�����',
   payer_fee            decimal(20,6) default 0 comment '���������',
   payer_account_type   varchar(30) comment '����˻�����(�ο��˻�����ö��:accounttypeenum)',
   receiver_user_no     varchar(15) comment '�տ��˱��',
   receiver_name        varchar(60) comment '�տ�������',
   receiver_pay_amount  decimal(20,6) default 0 comment '�տ֧�����',
   receiver_fee         decimal(20,6) default 0 comment '�տ������',
   receiver_account_type varchar(30) comment '�տ�˻�����(�ο��˻�����ö��:accounttypeenum)',
   order_ip             varchar(30) comment '�µ�ip(�ͻ���ip,�������л�ȡ)',
   order_referer_url    varchar(100) comment '���ĸ�ҳ�����ӹ�����(�����ڷ�թƭ)',
   order_amount         decimal(20,6) default 0 comment '�������',
   plat_income          decimal(20,6) comment 'ƽ̨����',
   fee_rate             decimal(20,6) comment '����',
   plat_cost            decimal(20,6) comment 'ƽ̨�ɱ�',
   plat_profit          decimal(20,6) comment 'ƽ̨����',
   return_url           varchar(600) comment 'ҳ��ص�֪ͨurl',
   notify_url           varchar(600) comment '��̨�첽֪ͨurl',
   pay_way_code         varchar(50) comment '֧����ʽ���',
   pay_way_name         varchar(100) comment '֧����ʽ����',
   pay_success_time     datetime comment '֧���ɹ�ʱ��',
   complete_time        datetime comment '���ʱ��',
   is_refund            varchar(30) default '101' comment '�Ƿ��˿�(100:��,101:��,Ĭ��ֵΪ:101)',
   refund_times         int default 0 comment '�˿����(Ĭ��ֵΪ:0)',
   success_refund_amount decimal(20,6) comment '�ɹ��˿��ܽ��',
   trx_type             varchar(30) comment '����ҵ������  �����ѡ���ֵ��',
   order_from           varchar(30) comment '������Դ',
   pay_type_code        varchar(50) comment '֧�����ͱ��',
   pay_type_name        varchar(100) comment '֧����������',
   fund_into_type       varchar(30) comment '�ʽ���������',
   remark               varchar(3000) comment '��ע',
   field1               varchar(500),
   field2               varchar(500),
   field3               varchar(500),
   field4               varchar(500),
   field5               varchar(500),
   bank_return_msg      varchar(2000) comment '���з�����Ϣ',
   primary key (id),
   unique key ak_key_2 (trx_no)
);

alter table rp_trade_payment_record comment '֧����¼��';

CREATE TABLE seq_table (SEQ_NAME varchar(50) NOT NULL, CURRENT_VALUE bigint DEFAULT '1000000002' NOT NULL, INCREMENT smallint DEFAULT '1' NOT NULL, REMARK varchar(100) NOT NULL, PRIMARY KEY (SEQ_NAME)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACCOUNT_NO_SEQ', 10000000, 1, '�˻�--�˻����');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACTIVITY_NO_SEQ', 10000006, 1, '�--����');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('USER_NO_SEQ', 10001113, 1, '�û�--�û����');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('TRX_NO_SEQ', 10000000, 1, '���ס�-֧����ˮ��');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('BANK_ORDER_NO_SEQ', 10000000, 1, '���ס�-���ж�����');
INSERT INTO seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('RECONCILIATION_BATCH_NO_SEQ', 10000000, 1, '���ˡ�-���κ�');

/*==============================================================*/
/* create function                                              */
/*==============================================================*/
CREATE FUNCTION `FUN_SEQ`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
     UPDATE SEQ_TABLE
     SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
     WHERE  SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;


CREATE FUNCTION `FUN_SEQ_CURRENT_VALUE`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE=0;
    SELECT CURRENT_VALUE INTO VALUE
    FROM SEQ_TABLE 
    WHERE SEQ_NAME=SEQ;
    RETURN VALUE;
END;

CREATE FUNCTION `FUN_SEQ_SET_VALUE`(SEQ VARCHAR(50), VALUE INTEGER) RETURNS BIGINT(20)
BEGIN
     UPDATE SEQ_TABLE 
     SET CURRENT_VALUE=VALUE
     WHERE SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;

CREATE FUNCTION  FUN_NOW()
 RETURNS DATETIME
BEGIN 
RETURN now();
END;


-- ʱ�亯��

CREATE FUNCTION `FUN_DATE_ADD`(STR_DATE VARCHAR(10), STR_INTERVAL INTEGER) RETURNS DATE
BEGIN
     RETURN date_add(STR_DATE, INTERVAL STR_INTERVAL DAY);
END;






-- -----------------------------------------------------------------------------------------------------------------------------------
--                                   ע�⣺�ýű�������mysql�����£�������������ݿ⣬������Ҫ�����޸ģ���ִ�С�                    --
--                                                                                           ��д�ˣ������   ��www.roncoo.com��    --
-- -----------------------------------------------------------------------------------------------------------------------------------

drop table if exists PMS_MENU;

drop table if exists PMS_MENU_ROLE;

drop table if exists PMS_OPERATOR;

drop table if exists PMS_OPERATOR_LOG;

drop table if exists PMS_PERMISSION;

drop table if exists PMS_ROLE;

drop table if exists PMS_ROLE_OPERATOR;

drop table if exists PMS_ROLE_PERMISSION;

create table PMS_MENU
(
   id                   bigint not null auto_increment,
   version              bigint not null,
   creater              varchar(50) not null comment '������',
   create_time          datetime not null comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20) not null,
   remark               varchar(300),
   is_leaf              varchar(20),
   level                smallint,
   parent_id            bigint not null,
   target_name          varchar(100),
   number               varchar(20),
   name                 varchar(100),
   url                  varchar(100),
   primary key (id)
)auto_increment = 1000;

alter table PMS_MENU comment '�˵���';


alter table PMS_MENU comment '�˵���';

create table PMS_MENU_ROLE
(
   id                   bigint not null auto_increment comment '����',
   version              bigint,
   creater              varchar(50) comment '������',
   create_time          datetime comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20),
   remark               varchar(300),
   role_id              bigint not null,
   menu_id              bigint not null,
   primary key (id),
   key AK_KEY_2 (role_id, menu_id)
) auto_increment = 1000;

alter table PMS_MENU_ROLE comment 'Ȩ�����ɫ������';

create table PMS_OPERATOR
(
   id                   bigint not null auto_increment comment '����',
   version              bigint not null,
   creater              varchar(50) not null comment '������',
   create_time          datetime not null comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20) not null,
   remark               varchar(300),
   real_name            varchar(50) not null,
   mobile_no            varchar(15) not null,
   login_name           varchar(50) not null,
   login_pwd            varchar(256) not null,
   type                 varchar(20) not null,
   salt                 varchar(50) not null,
   primary key (id),
   key AK_KEY_2 (login_name)
) auto_increment = 1000;

alter table PMS_OPERATOR comment '����Ա��';

create table PMS_OPERATOR_LOG
(
   id                   bigint not null auto_increment comment '����',
   version              bigint not null,
   creater              varchar(50) not null comment '������',
   create_time          datetime not null comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20) not null,
   remark               varchar(300),
   operator_id          bigint not null,
   operator_name        varchar(50) not null,
   operate_type         varchar(50) not null comment '�������ͣ�1:���ӣ�2:�޸ģ�3:ɾ����4:��ѯ��',
   ip                   varchar(100) not null,
   content              varchar(3000),
   primary key (id)
) auto_increment = 1000;

alter table PMS_OPERATOR_LOG comment 'Ȩ��_����Ա������־��';

create table PMS_PERMISSION
(
   id                   bigint not null auto_increment comment '����',
   version              bigint not null,
   creater              varchar(50) not null comment '������',
   create_time          datetime not null comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20) not null,
   remark               varchar(300),
   permission_name      varchar(100) not null,
   permission           varchar(100) not null,
   primary key (id),
   key AK_KEY_2 (permission),
   key AK_KEY_3 (permission_name)
) auto_increment = 1000;

alter table PMS_PERMISSION comment 'Ȩ�ޱ�';

create table PMS_ROLE
(
   id                   bigint not null auto_increment comment '����',
   version              bigint,
   creater              varchar(50) comment '������',
   create_time          datetime comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20),
   remark               varchar(300),
   role_code            varchar(20) not null comment '��ɫ���ͣ�1:��������Ա��ɫ��0:��ͨ����Ա��ɫ��',
   role_name            varchar(100) not null,
   primary key (id),
   key AK_KEY_2 (role_name)
) auto_increment = 1000;

alter table PMS_ROLE comment '��ɫ��';

create table PMS_ROLE_OPERATOR
(
   id                   bigint not null auto_increment comment '����',
   version              bigint not null,
   creater              varchar(50) not null comment '������',
   create_time          datetime not null comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20) not null,
   remark               varchar(300),
   role_id              bigint not null,
   operator_id          bigint not null,
   primary key (id),
   key AK_KEY_2 (role_id, operator_id)
) auto_increment = 1000;

alter table PMS_ROLE_OPERATOR comment '����Ա���ɫ������';

create table PMS_ROLE_PERMISSION
(
   id                   bigint not null auto_increment comment '����',
   version              bigint,
   creater              varchar(50) comment '������',
   create_time          datetime comment '����ʱ��',
   editor               varchar(50) comment '�޸���',
   edit_time            datetime comment '�޸�ʱ��',
   status               varchar(20),
   remark               varchar(300),
   role_id              bigint not null,
   permission_id        bigint not null,
   primary key (id),
   key AK_KEY_2 (role_id, permission_id)
) auto_increment = 1000;

alter table PMS_ROLE_PERMISSION comment 'Ȩ�����ɫ������';





-- ------------------------------step 1  �˵�-------------------------------------------------
-- �˵���ʼ������
--  -- �˵��ĳ�ʼ������
INSERT INTO PMS_MENU (id,version,status,creater,create_time, editor, edit_time, remark, NAME, URL, NUMBER, IS_LEAF, LEVEL, PARENT_ID, TARGET_NAME) VALUES 
(1,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', 'Ȩ�޹���', '##', '001', 'NO', 1, 0, '#'),
(2,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�˵�����', 'pms/menu/list', '00101', 'YES', 2, 1, 'cdgl'),
(3,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', 'Ȩ�޹���', 'pms/permission/list', '00102', 'YES',2, 1, 'qxgl'),
(4,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '��ɫ����', 'pms/role/list', '00103', 'YES',2, 1, 'jsgl'),
(5,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '����Ա����', 'pms/operator/list', '00104', 'YES',2, 1, 'czygl'),

(10,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�˻�����', '##', '002', 'NO', 1, 0, '#'),
(12,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�˻���Ϣ', 'account/list', '00201', 'YES',2, 10, 'zhxx'),
(13,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�˻���ʷ��Ϣ', 'account/historyList', '00202', 'YES',2, 10, 'zhlsxx'),


(20,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�û�����', '##', '003', 'NO', 1, 0, '#'),
(22,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�û���Ϣ', 'user/info/list', '00301', 'YES',2, 20, 'yhxx'),

(30,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '֧������', '##', '004', 'NO', 1, 0, '#'),
(32,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '֧����Ʒ��Ϣ', 'pay/product/list', '00401', 'YES',2, 30, 'zfcpgl'),
(33,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�û�֧������', 'pay/config/list', '00402', 'YES',2, 30, 'yhzfpz'),

(40,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '���׹���', '##', '005', 'NO', 1, 0, '#'),
(42,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '֧����������', 'trade/listPaymentOrder', '00501', 'YES',2, 40, 'zfddgl'),
(43,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '֧����¼����', 'trade/listPaymentRecord', '00502', 'YES',2, 40, 'zfjjgl'),

(50,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�������', '##', '006', 'NO', 1, 0, '#'),
(52,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '�����¼����', 'sett/list', '00601', 'YES',2, 50, 'jsjlgl'),

(60,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '���˹���', '##', '007', 'NO', 1, 0, '#'),
(62,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '���˲���б�', 'reconciliation/list/mistake', '00701', 'YES',2, 60, 'dzcclb'),
(63,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '���������б�', 'reconciliation/list/checkbatch', '00702', 'YES',2, 60, 'dzpclb'),
(64,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '���˻�����б�', 'reconciliation/list/scratchPool', '00703', 'YES',2, 60, 'dzhcclb');

-- ------------------------------step 2��Ȩ�޹��ܵ�-------------------------------------------------
-- Ȩ�޹��ܵ�ĳ�ʼ������


insert into PMS_PERMISSION (id,version,status,creater,create_time, editor, edit_time, remark, permission_name, permission) values 
 (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-�˵�-�鿴','Ȩ�޹���-�˵�-�鿴','pms:menu:view'),
 (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-�˵�-���','Ȩ�޹���-�˵�-���','pms:menu:add'),
 (3, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-�˵�-�鿴','Ȩ�޹���-�˵�-�޸�','pms:menu:edit'),
 (4, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-�˵�-ɾ��','Ȩ�޹���-�˵�-ɾ��','pms:menu:delete'),

 (11, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-Ȩ��-�鿴','Ȩ�޹���-Ȩ��-�鿴','pms:permission:view'),
 (12, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-Ȩ��-���','Ȩ�޹���-Ȩ��-���','pms:permission:add'),
 (13, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-Ȩ��-�޸�','Ȩ�޹���-Ȩ��-�޸�','pms:permission:edit'),
 (14, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-Ȩ��-ɾ��','Ȩ�޹���-Ȩ��-ɾ��','pms:permission:delete'),

 (21, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-��ɫ-�鿴','Ȩ�޹���-��ɫ-�鿴','pms:role:view'),
 (22, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-��ɫ-���','Ȩ�޹���-��ɫ-���','pms:role:add'),
 (23, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-��ɫ-�޸�','Ȩ�޹���-��ɫ-�޸�','pms:role:edit'),
 (24, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-��ɫ-ɾ��','Ȩ�޹���-��ɫ-ɾ��','pms:role:delete'),
 (25, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-��ɫ-����Ȩ��','Ȩ�޹���-��ɫ-����Ȩ��','pms:role:assignpermission'),

 (31, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-����Ա-�鿴','Ȩ�޹���-����Ա-�鿴','pms:operator:view'),
 (32, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-����Ա-���','Ȩ�޹���-����Ա-���','pms:operator:add'),
 (33, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-����Ա-�鿴','Ȩ�޹���-����Ա-�޸�','pms:operator:edit'),
 (34, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-����Ա-������ⶳ','Ȩ�޹���-����Ա-������ⶳ','pms:operator:changestatus'),
 (35, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','Ȩ�޹���-����Ա-��������','Ȩ�޹���-����Ա-��������','pms:operator:resetpwd'),


 (51, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�˻�����-�˻�-�鿴','�˻�����-�˻�-�鿴','account:accountInfo:view'),
 (52, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�˻�����-�˻�-���','�˻�����-�˻�-���','account:accountInfo:add'),
 (53, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�˻�����-�˻�-�鿴','�˻�����-�˻�-�޸�','account:accountInfo:edit'),
 (54, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�˻�����-�˻�-ɾ��','�˻�����-�˻�-ɾ��','account:accountInfo:delete'),

 (61, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�˻�����-�˻���ʷ-�鿴','�˻�����-�˻���ʷ-�鿴','account:accountHistory:view'),

 (71, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�û�����-�û���Ϣ-�鿴','�û�����-�û���Ϣ-�鿴','user:userInfo:view'),
 (72, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�û�����-�û���Ϣ-���','�û�����-�û���Ϣ-���','user:userInfo:add'),
 (73, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�û�����-�û���Ϣ-�鿴','�û�����-�û���Ϣ-�޸�','user:userInfo:edit'),
 (74, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�û�����-�û���Ϣ-ɾ��','�û�����-�û���Ϣ-ɾ��','user:userInfo:delete'),

 (81, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����Ʒ-�鿴','֧������-֧����Ʒ-�鿴','pay:product:view'),
 (82, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����Ʒ-���','֧������-֧����Ʒ-���','pay:product:add'),
 (83, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����Ʒ-�鿴','֧������-֧����Ʒ-�޸�','pay:product:edit'),
 (84, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����Ʒ-ɾ��','֧������-֧����Ʒ-ɾ��','pay:product:delete'),

 (85, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����ʽ-�鿴','֧������-֧����ʽ-�鿴','pay:way:view'),
 (86, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����ʽ-���','֧������-֧����ʽ-���','pay:way:add'),
 (87, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����ʽ-�鿴','֧������-֧����ʽ-�޸�','pay:way:edit'),
 (88, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧����ʽ-ɾ��','֧������-֧����ʽ-ɾ��','pay:way:delete'),

 (91, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧������-�鿴','֧������-֧������-�鿴','pay:config:view'),
 (92, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧������-���','֧������-֧������-���','pay:config:add'),
 (93, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧������-�鿴','֧������-֧������-�޸�','pay:config:edit'),
 (94, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','֧������-֧������-ɾ��','֧������-֧������-ɾ��','pay:config:delete'),

 (101, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-����-�鿴','���׹���-����-�鿴','trade:order:view'),
 (102, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-����-���','���׹���-����-���','trade:order:add'),
 (103, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-����-�鿴','���׹���-����-�޸�','trade:order:edit'),
 (104, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-����-ɾ��','���׹���-����-ɾ��','trade:order:delete'),

 (111, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-��¼-�鿴','���׹���-��¼-�鿴','trade:record:view'),
 (112, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-��¼-���','���׹���-��¼-���','trade:record:add'),
 (113, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-��¼-�鿴','���׹���-��¼-�޸�','trade:record:edit'),
 (114, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���׹���-��¼-ɾ��','���׹���-��¼-ɾ��','trade:record:delete'),

 (121, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�������-��¼-�鿴','�������-��¼-�鿴','sett:record:view'),
 (122, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�������-��¼-���','�������-��¼-���','sett:record:add'),
 (123, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�������-��¼-�鿴','�������-��¼-�޸�','sett:record:edit'),
 (124, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','�������-��¼-ɾ��','�������-��¼-ɾ��','sett:record:delete'),

 (131, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-���-�鿴','���˹���-���-�鿴','recon:mistake:view'),
 (132, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-���-���','���˹���-���-���','recon:mistake:add'),
 (133, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-���-�鿴','���˹���-���-�޸�','recon:mistake:edit'),
 (134, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-���-ɾ��','���˹���-���-ɾ��','recon:mistake:delete'),

 (141, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-����-�鿴','���˹���-����-�鿴','recon:batch:view'),
 (142, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-����-���','���˹���-����-���','recon:batch:add'),
 (143, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-����-�鿴','���˹���-����-�޸�','recon:batch:edit'),
 (144, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-����-ɾ��','���˹���-����-ɾ��','recon:batch:delete'),

 (151, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-�����-�鿴','���˹���-�����-�鿴','recon:scratchPool:view'),
 (152, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-�����-���','���˹���-�����-���','recon:scratchPool:add'),
 (153, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-�����-�鿴','���˹���-�����-�޸�','recon:scratchPool:edit'),
 (154, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','���˹���-�����-ɾ��','���˹���-�����-ɾ��','recon:scratchPool:delete');

-- -----------------------------------step3������Ա--------------------------------------------
-- -- ����Ա�ĳ�ʼ������
--  admin ��������Ա
insert into PMS_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark, login_name, login_pwd,real_name,mobile_no,type,salt) 
values (1, 0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '��������Ա', 'admin', 'd3c59d25033dbf980d29554025c23a75','��������Ա', '18620936193', 'ADMIN','8d78869f470951332959580424d4bf4f');

--  guest  �ο�
insert into PMS_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark, login_name, login_pwd,real_name,mobile_no,type,salt) 
values (2, 0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'guest','2016-06-03 11:07:43', '�ο�', 'guest', '3f0dbf580ee39ec03b632cb33935a363','�ο�', '18926215592', 'USER','183d9f2f0f2ce760e98427a5603d1c73');

-- ------------------------------------step4����ɫ-------------------------------------------
-- -- ��ɫ�ĳ�ʼ������
insert into PMS_ROLE (id,version,status,creater,create_time, editor, edit_time, remark, role_code, role_name) 
values (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43','��������Ա��ɫ','admin', '��������Ա��ɫ');

insert into PMS_ROLE (id,version,status,creater,create_time, editor, edit_time, remark, role_code, role_name) 
values (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'guest', '2016-06-03 11:07:43','�οͽ�ɫ','guest', '�οͽ�ɫ');

-- ------------------------------------step5������Ա�ͽ�ɫ����-------------------------------------------
-- -- ����Ա���ɫ�����ĳ�ʼ������

--  admin  ����admin ��test������ɫ
insert into PMS_ROLE_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',1,1);
insert into PMS_ROLE_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,1);

-- guest  �����οͽ�ɫ  ���οͽ�ɫֻ�в鿴���׼�¼����Ϣ��
insert into PMS_ROLE_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (3, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,2);

-- -------------------------------------step6����ɫ��Ȩ�޹���------------------------------------------
-- -- ��ɫ���û����ܵ�����ĳ�ʼ������

-- admin��ӵ�����е�Ȩ�޵㣩
insert into PMS_ROLE_PERMISSION  (role_id, permission_id) select 1,id from PMS_PERMISSION;


-- guest ��ֻ�����еĲ鿴Ȩ�ޣ�
insert into PMS_ROLE_PERMISSION (version,status,creater,create_time, editor, edit_time, remark,role_id,permission_id) 
values 
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,1),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,11),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,21),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,31),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,41),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,51),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,61),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,71),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,81),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,85),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,91),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,101),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,111),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,121),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,131),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,141),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,151);

-- -------------------------------------step7����ɫ�Ͳ˵�����------------------------------------------
--  ��ɫ����Ϣ������ʼ������
-- admin

insert into PMS_MENU_ROLE(role_id, menu_id) select 1,id from PMS_MENU;

-- guest  ���еĲ˵���ֻ�в鿴Ȩ�ޣ�
insert into PMS_MENU_ROLE (role_id, menu_id) select 2,id from PMS_MENU;

