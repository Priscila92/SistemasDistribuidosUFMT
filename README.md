# Trabalho Prático
Universidade Federal de Mato Grosso  
Instituto de Computação  
Curso de Ciência da Computação  
Disciplina: Sistemas Distribuídos  
Discente: Dr. Luís César Darienzo Alves   
Discentes: [Ana Novello](https://github.com/AnaNovello), [Brendo Melo](https://github.com/BrBrendo), [Karla Souza](https://github.com/KarlaSouza), [Priscila Lopes](https://github.com/Priscila92) e [Rafaela Francisco](https://github.com/RafaelasFrancisco)  
## Compartilhador de Arquivos ##
O objetivo deste projeto é desenvolver um software que possibilite usuários compartilharem arquivos entre si, envolvendo o envio e recebimento de partes de um arquivo por meio de uma rede de comunicação. Dessa forma, um usuário pode receber um arquivo segmentado em múltiplas partes de várias fontes e, a partir disso, o software receptor deve ser capaz de reagrupar essas partes para fornecer ao usuário um único arquivo.

# Arquitetura
O software foi dividido em três módulos principais:

- **Server**: Responsável por armazenar informações sobre arquivos na rede, responder a consultas sobre arquivos e propagar informações para outros módulos Server.

- **Client**: Encarregado de publicar informações sobre arquivos contidos em um diretório e de segmentar e enviar partes de arquivos solicitados.

- **Frontend**: Lida com a interação entre o usuário e o sistema, incluindo a busca de informações sobre arquivos no módulo Server e a solicitação de segmentos de arquivo.

Foram utilizadas as tecnologias de Web Services e Sockets para comunicação entre módulos.

## Diagramas
Também foram construídos diagramas do projeto para fortalecer os padrões de modelagem, documentação e comunicação do software. Para este projeto, foram elaborados três tipos de diagramas UML:

- **Diagramas de Casos de Uso**
- **Diagramas de Sequência**
- **Diagramas de Classes**

Os diagramas estão localizados em nossa página da [Wiki](https://github.com/Priscila92/SistemasDistribuidosUFMT/wiki).

