# Requisitos
- [] Usuário **ADMIN** deve ser capaz de:
  - [x] Cadastrar e desativar usuários
  - [] CRUD de refeições

- [] Usuário **SERVIDOR** deve ser capaz de:
  - [] Cadastrar e editar refeições
    - [] Mas só pode apagar refeições criadas por ele mesmo
  - [] Validar presença de outros usuários ao refeitório

- [] Usuário **ALUNO**  deve ser capaz de:
  - [] Reservar refeição
  - [] Cancelar refeição

- [] O sistema deve garantir que:
  - [] Somente alunos reservem tickets
  - [] Uma reserva so pode ser efetuada entre 3hrs a 1hr antes do início da refeição
  - [] Uma refeição só pode ser cancelada no mesmo período que a reserva está disponível
  - [] Se um aluno perder DUAS refeições dentro de um bimestre, bloquear o acesso dele


## Ordem de implementação
### [x] Autenticação
### [x] Crud de usuário
### [] Crud de refeição
### [] Niveis de acesso para CRUD de usuário e CRUD de refeições
### [] Funcionalidade de ALUNO
### [] Funcionalidade de SERVIDOR
### Documentation com Swagger