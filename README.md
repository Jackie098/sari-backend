# Requisitos
- [x] Usuário **ADMIN** deve ser capaz de:
  - [x] Cadastrar e desativar usuários
  - [x] CRUD de refeições

- [x] Usuário **SERVIDOR** deve ser capaz de:
  - [x] Cadastrar e editar refeições
    - [] Mas só pode apagar refeições criadas por ele mesmo
  - [x] Mudar a disponibilidade da refeição (status do **ticket_meals**)
  - [x] Validar presença de um aluno ao refeitório
    - [x] Somente quando o **ticket_meals** status = AVAILABLE
      - [x] E o *user.isBlocked = false*

- [x] Usuário **ALUNO**  deve ser capaz de:
  - [x] Reservar refeição
    - [x] Validar ticket a partir da *hora* e *ticket_meals status = SCHEDULED*
    - [x] Atualizar a quantidade de tickets disponíveis
    - [x] Criar registro na tabela **book_meal**
  - [x] Cancelar refeição
    - [x] Validar ticket a partir da *hora* e *ticket_meals status = SCHEDULED*
    - [x] Atualizar a quantidade de tickets disponíveis
    - [x] Atualizar registro na tabela **book_meal**

- [] O sistema deve garantir que:
  - [x] Somente alunos reservem tickets
  - [x] Uma reserva so pode ser efetuada entre 2hr a 1hr antes do início da refeição
  - [x] Uma refeição só pode ser cancelada no mesmo período que a reserva está disponível
  - [] Se um aluno perder DUAS refeições dentro de um bimestre, bloquear o acesso dele
  - [] Usuário com acesso bloqueado não devem ser capazes de utilizar o sistema


## Ordem de implementação
- [x] Criar usuário ADM
- [x] Autenticação
- [x] Crud de usuário
- [x] Crud de refeição
- [x] Niveis de acesso para CRUD de usuário e CRUD de refeições
- [x] Funcionalidade de SERVIDOR
- [x] Funcionalidade de ALUNO
- [x] Change small ints to STRINGS in Enum's
- [] Refatoração dos DTO's - Validação de input de dados e mapeamento modularizado
- [] Padronizando o tratamento de exceção
- [] Documentation com Swagger
- [] Corrigindo WARN's in log
- [] Buscas com filtro e Lazy Queries
- [] Paginação
- [] Change Date to timestamps
- [] Add seeds and migrations
- [] Unit Tests

# Explicação dos Enum's
- TicketMealStatusEnum
  - SCHEDULED
    - Quando a refeição é criada e até o momento que o refeitório abrirá
  - AVAILABLE
    - Quando o refeitório estiver aberto (horário de início) até o momento de encerramento (horário de término da refeição)
  - PAUSED
    - Status que não pertence ao ciclo de vida comum do status da refeição. Ao selecionar este status, um aluno não pode agendar um ticket e nem um servidor poderá validar a presença de um aluno ao refeitório
  - BLOCKED
    - Quando um registro de refeição não estará mais disponível no menu da semana
  - FINISHED
    - Quando um registro de refeição é finalizado. Nesse momento alunos e servidores não podem fazer nenhuma ação