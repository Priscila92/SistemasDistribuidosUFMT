# Trabalho Prático
Universidade Federal de Mato Grosso  
Instituto de Computação  
Curso de Ciência da Computação  
Disciplina: Dr. Luís César Darienzo Alves  
Discentes: [Ana Novelo](https://github.com/ana380s), [Brendo Melo](https://github.com/BrBrendo), [Karla Souza](https://github.com/KarlaSouza), [Priscila Lopes](https://github.com/Priscila92) e [Rafaela Francisco](https://github.com/RafaelasFrancisco)  
## Compartilhador de Arquivos ##
O objetivo deste projeto foi desenvolver um software que possibilite usuários compartilharem arquivos entre si, envolvendo o envio e recebimento de partes de um arquivo por meio de uma rede de comunicação. Dessa forma, um usuário pode receber um arquivo segmentado em múltiplas partes de várias fontes e, a partir disso, o software receptor deve ser capaz de reagrupar essas partes para fornecer ao usuário um único arquivo.

# Arquitetura
O software foi dividido em três módulos principais:

- **Server**: Responsável por armazenar informações sobre arquivos na rede, responder a consultas sobre arquivos e propagar informações para outros módulos Server.

- **Client**: Encarregado de publicar informações sobre arquivos contidos em um diretório e de segmentar e enviar partes de arquivos solicitados.

- **Frontend**: Lida com a interação entre o usuário e o sistema, incluindo a busca de informações sobre arquivos no módulo Server e a solicitação de segmentos de arquivo.

Foram utilizadas as tecnologias de Web Services e Sockets para comunicação entre módulos.


