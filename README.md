Este projeto se trata de um trabalho realizado para a disciplina de Teoria dos Grafos do curso de Ciência da Computação da Universidade Federal de Juiz de Fora.

O trabalho foi realizado individualmente na linguagem Kotlin.

Descrição dos requisitos:

TRABALHOS DE DCC059 – TEORIA DOS GRAFOS – 2023.3

PROF. MARCOS PASSINI
DCC/UFJF

>>Definição.

(1) A avaliação nesta disciplina consiste em duas provas teóricas, responsáveis por metade da
nota, e um trabalho de implementação computacional, descrito aqui.

(2) O trabalho consiste em:
(a) Estrutura de dados para representação computacional de grafos.
(b) Funções de entrada e saída de dados.
(c) Algoritmos indicados para diversos problemas em grafos.

(3) Para o prazo final será proposto um cronograma de apresentações entre os dias 11 e 15 de
dezembro.

(4) O trabalho pode ser executado individualmente ou em duplas.

>>Implementação.

(1) A implementação pode ser feita na linguagem de escolha dos alunos.

(2) Não será permitido o uso de bibliotecas que forneçam entrada/saída ou algoritmos já imple-
mentados.

(3) A implementação eve ser o mais simples possível, sem preocupações com interface complexa
com usuário ou entrada e saída sofisticada.

(4) À escolha do aluno, podem-se usar classes em programação orientada a objetos, matrizes
básicas ou continentes em programação genérica, ou ainda outro paradigma.

>>Apresentação.

Os trabalhos devem ser apresentados ao professor no dia e horário marcados. Na
apresentação o programa deve funcionar com entrada fornecida na hora pelo professor. Os autores
serão arguídos sobre o código-fonte, sobre detalhes de implementação e de funcionamento dos
algoritmos e sobre exceções ou erros que ocorram. Além disso, podem ser solicitadas pequenas
modificações no código. Nos casos caso de trabalho feito em duplas, os dois membros devem ter
familiaridade com todo o trabalho.

>>Entrega.

Embora nota seja atribuída sobre a apresentação somente, todo o código deve ser entregue
em um arquivo zip depositado na atividade correspondente, na página da turma no Google Class-
room.
Prazos e versões intermediárias. Antes do prazo final, podem ser apresentadas versões intermediá-
rias ou incompletas do trabalho, para aconselhamento ou orientação. No prazo final, entretanto, a
apresentação será definitiva. Não será permitido corrigir erros no trabalho durante a apresentação.
Avaliação. Na avaliação serão considerados os seguintes itens:

(1) conhecimento teórico sobre o tópico;

(2) entrada e saída e adesão aos requisitos;

(3) implementação dos algoritmos;

(4) apresentação, desenvoltura e familiaridade com o trabalho.

Os dois membros da dupla serão avaliados independentemente e podem receber notas diferentes.
A falta de familiaridade com o código pode indicar trabalho buscado pronto na internet, trabalho
copiado de terceiros ou ainda trabalho feito por apenas um membro da dupla. Qualquer desses casos
invalida o trabalho.

>>1. BASE

Consiste em implementação de uma estrutura de dados para representação e manipulação com-
putacional de grafos:

(1) Função de entrada por leitura de arquivo em formato ASCII (.txt) da matriz de adjacências
ou matriz de distâncias, conforme o caso, no formato especificado ao final deste documento.

(2) Estruturas de dados para representação interna do grafo em matriz de adjacências, matriz de
incidências e tabela de incidências.

(3) Funções de transformação das representações acima de umas em outras.

(4) Funções de saída na tela, mostrando as três representações.

(5) Funções de saída por arquivo ASCII, semelhante ao formato de entrada.

Obs: A entrada consistirá em grafos simples (sem multigrafos nem hipergrafos), orientados ou
não-orientados, valorados ou não.

>>2. ALGORITMOS

Consiste nos seguintes algoritmos a rodarem sobre as estruturas definidas acima:

(1) Busca em largura

(2) Busca em profundidade

(3) Prim para árvore geradora mínima

(4) Dijkstra para caminho mínimo

(5) Ordenação topológica

(6) Ciclo euleriano

A seguir explicitam-se e exemplificam-se os formatos para entrada de dados:
Busca em largura e busca em profundidade:
Entrada: grafo não-orientado e vértice inicial
Saída: vértices em ordem de visitação
Prim para árvore geradora mínima:
Entrada: grafo não-orientado valorado
Saída: lista de arestas e peso total da árvore
Dijkstra para caminho mínimo:
Entrada: grafo não-orientado valorado e vértice inicial
Saída: lista de caminhos v. inicial até cada um dos outros e peso de cada caminho
Ordenação topológica:
Entrada: grafo orientado valorado
Saída: vértices ordenados
Ciclo euleriano:
Entrada: grafo não-orientado
Saída: arestas do ciclo na ordem do ciclo (cada aresta é adjacente à anterior e à posterior)
Obs: o vértice inicial, quando for o caso, deve ser selecionado pelo teclado (console).

>>FORMATO DOS ARQUIVOS DE ENTRADA

Grafos simples, valorados. Arquivo texto. A primeira linha contém um número inteiro que indica
o número de vértices. Da segunda linha em diante, apresenta-se a matriz de distâncias (número
inteiro entre -100 e 100). Os arcos inexistentes são representados pela constante 999 (103 − 1). A
diagonal principal e a triangular inferior devem conter apenas zeros. Os elementos em cada linha
são separados por espaços ou tabulações.


TRABALHOS DE DCC059 – TEORIA DOS GRAFOS – 2023.3 3
Digrafos simples, valorados. Arquivo texto. A primeira linha contém um número inteiro que indica
o número de vértices. Da segunda linha em diante, apresenta-se a matriz de distâncias (número
inteiro entre -100 e 100). Os arcos inexistentes são representados pela constante 999 (103 − 1). A
diagonal principal deve conter apenas zeros. Os elementos em cada linha são separados por espaços
ou tabulações.
