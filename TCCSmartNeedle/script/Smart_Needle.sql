drop database if exists Smart_Needle;

/* Cria o banco Smart_Needle*/

Create database if not exists Smart_Needle;
use Smart_Needle;

/* Tabela de Login*/

create table if not exists login(
	logCodigo int(5) primary key auto_increment,
	logTipo varchar(20),
	logLogin varchar(30),
	logDataLogin datetime not null,
    logDataLogout datetime not null,
    logTempoLogado varchar(30) not null
);
drop table login;
insert into login() values(not null, 'Adm', 'Joaozin', '2017-07-03 18:40:45', '0000-00-00 00:00:00');
insert into login() values(not null, 'Fun', 'Fani', '2017-07-03 18:40:45', '0000-00-00 00:00:00');
insert into login() values(not null, 'Fun', 'Fani', '2017-07-03 18:40:45', '0000-00-00 00:00:00', '0-0-0');

SET SQL_SAFE_UPDATES =  0; -- Para funcionar a atualização dos atributos que não são chaves primárias

insert into login() values(not null, 'tipo', 'login', '0000-00-00 00:00:00.000', '2017-06-30 20:30:10.123', '0000-01-01 00:00:00');

select logCodigo, logDataLogin from login where logTempoLogado = 'Logado';
select * from login;
 -- OBS: Administrador pode logar em vários PC's então ele será autenticado normalmente
 -- Ao contrário do Funcionario que não pode logar se já estiver logado, então na sua classe de DAO
 -- ao autenticar o login, será executado um select na tabela com o valor do objeto login.getLogin() e verificar
 -- se a dataHora de logout é igual 0000-00... (ou seja se for isso está logado) e se tiver uma data não nula
 -- ela não está mais logado, lembrando que será feito um update na tabela login quando o Funcionario ou Adm sair do sistema
 -- alterando a data nula da tabela pela data que ele sair, isso atualiza a tabela
 -- Caso o Adm queira deletar os valores desta tabela pelo formulário, deletando uma linha de login de um Funcionario
 -- , que só pode logar uma vez, portanto será deletado na tabela Login a linha onde houver uma dataLogin e um 'login' iguais aos valores 
 -- selecionados, e caso o Adm esteja deletando uma linha de um Adm será deletada pelo codLogin já que o Adm pode logar duas 
 -- vezes ao mesmo tempo gerando dois logins e dataLogin iguais

-- Tabela Administrador
create table if not exists administrador(
	admId int(5) primary key auto_increment,
	admNome varchar(40) null,
	admEmail varchar(50) null, 
	admTelefone varchar(15)null,
	admRg varchar(20) null,
	admCpf varchar(15) null,
	admSexo varchar(10),
	admLogin varchar(30),
	admSenha varchar(50),
	admUf varchar(20),
	admEndereco varchar(100),
	admCidade varchar(100),
	admBairro varchar(30),
	admCep varchar(20),
	admDataNascimento datetime null
);

insert into administrador() values(not null, 'Joao Mello', 'joao@gmail.com', '32342323', '23342', '34234', 'Masculino', 'j', 'a', 'SP',
	'Rua Siriema', 'Monte Mor', 'Jardim Itapuã', '13190-000', '2000-06-30 00:00:00');
    
insert into administrador() values(not null, 'Luana', 'luana@gmail.com', '(19)3979-1222', '53.364.442-2', '490.833.368-83',
 'Feminino', 'l', 'a', 'SP','Rua Siriema', 'Hortolândia', 'Jardim Remanso', '13190-000', '2000-06-30 00:00:00');

select * from administrador;

/*Tabela Funcionario*/
create table if not exists funcionario(
	funId int(5) primary key auto_increment,
	funNome varchar(40) null,
	funEmail varchar(50) null,
	funTelefone varchar(15)null,
	funRg varchar(20) null,
	funCpf varchar(15) null,
	funSexo varchar(15),
	funLogin varchar(30),
	funSenha varchar(50),
	funUf varchar(20),
	funEndereco varchar(100),
	funCidade varchar(100),
	funBairro varchar(30),
	funCep varchar(20),
	funDataNascimento datetime null,
	funNumeroDocumento varchar(25) null,
	funNumeroUnidade varchar(5)null
);

select * from funcionario where funNome LIKE '%m%';

drop table if exists funcionario;

select funID from funcionario;

delete from funcionario where funId = 2;

insert into funcionario() values(2, 'j', 'coao@gmail.com', '39791222', '533644422', '12345678912',
'femea', 'l', 'a', 'SP', 'Rua Siriema', 'Monte Mor', 'Jardim Itapuã', '13190-000', '2000-06-30 00:00:00', '13sd-23', '44444');
desc Funcionario;
select * from funcionario;

/*Tabela Paciente*/

create table Paciente(
	pacId int(5) primary key auto_increment,
	pacNome varchar(40),
	pacEmail varchar(50),
	pacTelefone varchar(15),
	pacRg varchar(20),
	pacCpf varchar(15),
	pacSexo varchar(10),
	pacLogin varchar(30),
	pacSenha varchar(50),
	pacUf varchar(20),
	pacEndereco varchar(100),
	pacCidade varchar(100),
	pacBairro varchar(30),
	pacCep varchar(20),
	pacDataNascimento datetime,
	pacHistorico varchar(500),
	pacNumeroProntuario varchar(20),
	pacProximaVacina datetime
);

insert into Paciente() values (1, not null,not null,not null,not null,not null,not null,not null,not null,not null,not null,not null,not null,not null,not null,not null,'123',not null,not null);
insert into paciente() values(1,'joao', 'joaomellogomes@gmail.com', '1939791222', '53.364.442-2', '490.833.368-83',
'Masculino', 'j', 'a', 'SP', 'Rua Seriema Nº225', 'Monte Mor', 'Jardim Itapuã','13190-000', '2000-06-30 00:00:00', 'historico', 'num2', '2018-01-22 00:00:00');
desc Paciente;
select * from Paciente;

-- Distribuidor
drop table if exists distribuidor;
create table if not exists distribuidor(
	disId int (5) primary key auto_increment,
    disNome varchar(50),
    disCnpj varchar(20),
    disEmail varchar(50),
    disTelefone varchar(20),
    disUf varchar(20),
	disEndereco varchar(100),
	disCidade varchar(100),
	disBairro varchar(30),
	disCep varchar(20)    
);

insert into distribuidor() values(not null, 'MaisUmDIs', '999999999999999', 'email', 'telefone', 'uf', 'Endereço', 
'cidade', 'bairro', 'cep');

update distribuidor set disCnpj = '99999999999999' where disId = 2;

select * from distribuidor;

/*Vacina*/

drop table if exists vacina;
create table if not exists vacina(
	vacId int(5) primary key auto_increment,
	vacFabricante varchar(25),
	vacLote varchar(15),
	vacDisponivel int(5),
	vacSolicitacoes int(5),
	vacValidade datetime,
    vacAnos int(5),
    vacMeses int(3),
	vacIndicacao varchar(500),
    vacContraIndicacao varchar(500),
	vacTipo varchar(20) null,
	vacNome varchar(20),
	vacDescricao varchar(500),
    disId int (5),
    Constraint FK_vacina_distribuidor_disId Foreign key (disId) References distribuidor (disId)
);

insert into vacina() values (not null, 'EMS', 'dwf34', 22, 22, '2017-09-12 00:00:00', 2012, 12, 'indicacao',
'contra indicacao', 'Aplicada', 'Ceurose', 'essaVacina é fantastica', 1);

update vacina set vacNome = 'Vacinja' where vacNome = 'Ceurose';

select * from vacina where vacNome collate  utf8_bin = 'Aurier';

select * from vacina;

-- Vacinas Aplicadas

drop table if exists vacinaAplicada;

create table if not exists vacinaAplicada(
	vacAplId int (5) primary key auto_increment,
	vacAplDataAplicada datetime,
	vacAplDose varchar(10),
	vacId int(5),
	Constraint FK_vacinaAplicada_vacina_vacId Foreign key (vacId) References vacina (vacId),
	disId int (5),
	Constraint FK_vacinaAplicada_distribuidor_disId Foreign key (disId) References distribuidor (disId),
	pacId int (5),
	Constraint FK_vacinaAplicada_paciente_idPac Foreign key (pacId) References paciente (pacId),
    funId int(5),
    Constraint FK_vacinaAplicada_funcionario_funID Foreign key(funId) references funcionario(funId)
);

select * from vacinaAplicada;

-- Email
create table email(
	emaId int(5) primary key auto_increment,
    emaEnderecoServidor varchar(50),
    emaPorta int(10),
    emaRemetente varchar(100),
    emaDestinatario varchar(100),
    emaAssunto varchar(500),
    emaMensagem varchar(1000)
);

/*Relatório*/

create table if not exists Relatorio(
relId int(5) primary key auto_increment,
relTotalVacinasAplicadas int(255)null,
relFaltaVacina int (255) null,
paciente_Cadastrados int (255)null,
total_Inadimplentes int(255) null,
funId int (5)null,
constraint FK_relatorio_funcionario_funId foreign key (funId) references funcionario (funId)
);

insert into Relatorio(id_Relatorio, total_Vac_Apli,falta_Vacina,paciente_Cadastrados, total_Inadimplentes,id_func ) values (not null, not null,not null,not null,not null,not null);
desc Relatorio;
select * from Relatorio;

/*Envio de SMS-EMAIL*/

create table Envio_SMS(
id_sms int(5) primary key auto_increment,
data_envio date  null,
retorno_envio boolean null,
assunto varchar(100),
corpo_email varchar (500),
id_func int (5)null
);

insert into Envio_SMS(id_sms, data_envio, retorno_envio,id_func) values (not null, not null, not null,not null);
desc Envio_SMS;
select * from  envio_SMS;

create table Contato(
id_cont int (5) primary key auto_increment,
email varchar(100),
nome varchar (50),
mensagem varchar (200)
);

select * from Contato;

select * from Login inner join Funcionario on Login.id_Login = Login.id_Login;
select * from Login inner join Paciente on Login.id_login = Login.id_login;
select * from Login inner join Vacina on Login.id_login = Login.id_login;



