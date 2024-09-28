# Projeto-FCCPD <img align="center" alt="Java" height="45" width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" /> <img align="center" alt="Python" height="45" width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/python/python-original.svg" /> <img lign="center" alt="Rabbit" height="45" width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/rabbitmq/rabbitmq-original.svg" />
<h2>Repositório destinado a aplicação utilizada no projeto da cadeira de FCCPD, CESAR School - 2024.2</h2>
<h3>Descrição</h3>
Este código representa um esquema de administração de um time de futebol. O servidor representa a gestão do clube, que envia as mensagens para os seguintes usuários (clientes):<br>
<table>
  <tr>- Jogadores (Players)</tr><br>
  <tr>- Treinadores (Coaches)</tr><br>
  <tr>- Setor médico (Medical)</tr><br>
  <tr>- Zeladores (Janitors)</tr><br>
  <tr>- Setor de marketing (Social Media)</tr><br>
  <tr>- Setor financeiro (Financial)</tr>
</table>
Além das mensagens enviadas para os clientes específicos, elas também podem ser enviadas para todos os usuários, e são todas salvas no log da auditoria.
<h3>Como rodar</h3>
<table>
  <tr>- Inicialmente, você deve clonar o repositório em seu computador através do comando: 
    <dt>

      git clone https://github.com/paulo-campos-57/FCCPD-Project.git
  </dt>
  </tr>
  <tr>- Uma vez clonado, você deve iniciar o servidor no arquivo Server.java</tr><br>
  <tr>- Em seguida, você deve iniciar, em outro terminal, o arquivo do cliente, client.py</tr><br>
  <tr>- No terminal do cliente, selecione o tipo de usuário que deseja cadastrar</tr><br>
  <tr>- Em seguida, no terminal do servidor, selecione o tipo de mensagem que será enviada</tr><br>
  <tr>- Ao enviar a mensagem, ela será salva no log da auditoria, e enviada para os clientes específicos</tr><br>
</table>
<h3>Requisitos</h3>
<table>
  <tr>- Para rodar esse programa, é necesário ter instalada a biblioteca de Python pika, através do seguinte comando:<dt>

      pip install pika
  </dt>
  </tr>
</table>
<h3>Desenvolvedores</h3>
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/AlbertAsmervik">
        <img src="https://avatars.githubusercontent.com/u/126566738?v=4" width="100px;" alt="Foto Albert"/><br>
        <sub>
          <b>Albert Asmervik</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kal-0">
        <img src="https://avatars.githubusercontent.com/u/106926790?v=4" width="100px;" alt="Foto Caio"/><br>
        <sub>
          <b>Caio Cesar</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/DiogoHMC">
        <img src="https://avatars.githubusercontent.com/u/116087739?v=4" width="100px;" alt="Foto Diogo"/><br>
        <sub>
          <b>Diogo Henrique</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/EstelaLacerda">
        <img src="https://avatars.githubusercontent.com/u/117921412?v=4" width="100px;" alt="Foto Stora"/><br>
        <sub>
          <b>Estela Lacerda</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/paulo-campos-57">
        <img src="https://avatars.githubusercontent.com/u/77108503?v=4" width="100px;" alt="Foto Megas"/><br>
        <sub>
          <b>Paulo Campos</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

# FCCPD-Project <img align="center" alt="Java" height="45" width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" /> <img align="center" alt="Python" height="45" width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/python/python-original.svg" /> <img lign="center" alt="Rabbit" height="45" width="55" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/rabbitmq/rabbitmq-original.svg" />
<h2>Repository for the application used in the FCCPD course project, CESAR School - 2024.2</h2>
<h3>Description</h3>
This code represents a management scheme for a football team. The server represents the club's management, which sends messages to the following users (clients):
<table>
  <tr>- Players</tr><br>
  <tr>- Coaches</tr><br>
  <tr>- Medical</tr><br>
  <tr>- Janitors</tr><br>
  <tr>- Social Media</tr><br>
  <tr>- Financial</tr>
</table>
In addition to the messages sent to specific clients, they can also be sent to all users, and all messages are saved in the audit log.
<h3>How to run</h3>
<table>
  <tr>- First, you should clone the repository to your computer using the command:
    <dt>

      git clone https://github.com/paulo-campos-57/FCCPD-Project.git
  </dt>
  </tr>
  <tr>- Once cloned, you should start the server in the Server.java file.</tr><br>
  <tr>- Next, you should start the client file, client.py, in another terminal.</tr><br>
  <tr>- In the client terminal, select the type of user you want to register.</tr><br>
  <tr>- Then, in the server terminal, select the type of message to be sent.</tr><br>
  <tr>- When you send the message, it will be saved in the audit log and sent to the specific clients.</tr><br>
</table>
<h3>Requirements</h3>
<table>
  <tr>- To run this program, you need to have the Python library pika installed using the following command:<dt>

      pip install pika
  </dt>
  </tr>
</table>
<h3>Developers</h3>
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/AlbertAsmervik">
        <img src="https://avatars.githubusercontent.com/u/126566738?v=4" width="100px;" alt="Foto Albert"/><br>
        <sub>
          <b>Albert Asmervik</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kal-0">
        <img src="https://avatars.githubusercontent.com/u/106926790?v=4" width="100px;" alt="Foto Caio"/><br>
        <sub>
          <b>Caio Cesar</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/DiogoHMC">
        <img src="https://avatars.githubusercontent.com/u/116087739?v=4" width="100px;" alt="Foto Diogo"/><br>
        <sub>
          <b>Diogo Henrique</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/EstelaLacerda">
        <img src="https://avatars.githubusercontent.com/u/117921412?v=4" width="100px;" alt="Foto Stora"/><br>
        <sub>
          <b>Estela Lacerda</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/paulo-campos-57">
        <img src="https://avatars.githubusercontent.com/u/77108503?v=4" width="100px;" alt="Foto Megas"/><br>
        <sub>
          <b>Paulo Campos</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
