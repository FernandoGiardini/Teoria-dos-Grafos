@file:Suppress("MemberVisibilityCanBePrivate")

import java.io.File
import java.nio.charset.Charset
import java.util.Scanner

class Grafo(nomeDoArquivo:String){
    val matrizDist:List<Int>
    val matrizAdj:List<Int>
    val vertices:Int
    val arestas:List<Triple<Int,Int,Int>>
    val matrizIncid:List<Int>
    val tabelaIncid:List<Int>

    init { //Como a criação de algumas representações necessita ou é facilitada por outras, inicializo todas com este bloco durante a instanciação do objeto
        matrizDist = geraMatrizDist(nomeDoArquivo)
        matrizAdj = geraMatrizAdj(matrizDist)
        vertices = matrizDist[0]
        arestas = geraArestas(matrizDist)
        matrizIncid = geraMatrizIncid(arestas,vertices)
        tabelaIncid = geraTabelaIncid(arestas)
    }
}

//Verificação do tipo do grafo

fun Grafo.verificaTipo():String{//Função de extensão!
    var contador1=0
    var contador2=0
    val vertices = this.matrizDist[0]
    val tamanho = this.matrizDist.size
    var veredito=""

    this.matrizDist.mapIndexed{indice,it ->
        if(it == this.matrizAdj[indice]){//comparando se a matriz de distâncias provida pelo arquivo é igual a de adjacências, sabemos se o grafo é valorado ou não valorado
            contador1++
        }
        if (indice == tamanho-vertices+contador2 && it ==0){//verificando se as N(numVértices) ultimas posições da matriz são 0, sabemos que o objeto de estudo é um grafo, caso contrário será um digrafo
            contador2++
        }
    }

    if(contador2 == vertices){
        veredito+="Grafo simples"
    }else{
        veredito+="Digrafo simples"
    }

    if (contador1 != tamanho){
        veredito+=" e Valorado"
    }

    return veredito
}


//Setor de Impressões e Funções auxiliares.


fun imprimirMatrizMN(matriz: List<Int>, arestas: Int){

    val quantEspaco = matriz.maxOrNull()?.toString()?.length ?:1
    for (i in matriz.indices){//impressão
        val formato = "%${quantEspaco+1}d "
        print(String.format(formato,matriz[i]))
        if (i%arestas == arestas-1)
            print("\n")//Usar a função vazia [ println() ] não funcionava ao escrever no arquivo, somente no terminal
                       // Acho que por estar vazia o printStream considerava como conteudo nulo e não computava a quebra de linha


    }
}
fun imprimirMatrizQuadrada(matriz:List<Int>){

    val quantEspaco = matriz.maxOrNull()?.toString()?.length ?:1
    for (i in matriz.indices){//impressão
        val formato = "%${quantEspaco}d "
        print(String.format(formato,matriz[i]))
        if (i%matriz[0]==0)
            print("\n")
    }
}
fun Grafo.imprimeArestas(){//Usado em desenvolvimento
    println("Arestas organizadas em trios (x,y,z) onde x é o vértice de origem da aresta , y o vértice de destino e z é o peso da aresta" )
    println(this.arestas.toString())
}

fun geraArestas(MatrizDist:List<Int>):List<Triple<Int,Int,Int>>{
    val vertices = MatrizDist[0]
    val arestas = mutableListOf<Triple<Int,Int,Int>>()
    var saiDe:Int
    var entraEm:Int

    MatrizDist.drop(1).mapIndexed{ indice, it->
        if(it != 999 && it != 0){

            saiDe = (indice/vertices)+1 //Linha da matriz
            entraEm = vertices - (((saiDe*vertices)-1)-indice) //Coluna da matriz

            arestas.add(Triple(saiDe,entraEm,it))
        }
    }
    return arestas
}

fun escolheVerticeInicial(teclado:Scanner,vertices:Int):Int{
    var input:Int

    do {
        println("Selecione um vertice entre 1 e $vertices para ser o vertice inicial da busca:")
        input = teclado.nextLine()?.toIntOrNull()?: 0

        if (input !in 1 .. vertices)
            println("Entrada inválida")

    }while (input !in 1 .. vertices)

    return input
}





//Geração de Representações


fun geraMatrizDist(nomeDoArquivo: String): List<Int> { //Matriz originadora do grafo e transforma de texto em números para operarmos depois
    val string =
        File("C:\\Users\\ferna\\IdeaProjects\\untitled\\src\\main\\kotlin\\RepositorioDeGrafos\\${nomeDoArquivo}").readText(
            Charset.defaultCharset()
        )

    return "\\d+".toRegex() //Regex que corresponde a 1 ou mais dígitos em sequência
        .findAll(string) //função que encontra todas as ocorrencias da regex da linha anterior na string provida
        .map { it.value.toInt() } //mapeamento de cada ocorrencia da regex restante, de tipo string para tipo inteiro
        .toList()
}

fun geraMatrizAdj(MatrizDist:List<Int>):List<Int>{
    val matrizAdj = MatrizDist
        .mapIndexed {indice,it ->
        if(it == 999||it == 0||indice==0){
            it
        }else{
            1
        }
    }
    return matrizAdj
}

fun Grafo.geraMatrizIncid(arestas:List<Triple<Int,Int,Int>>,vertices: Int):List<Int>{

    val ehDigrafo = this.verificaTipo().contains("Digrafo")

    val matrizIncid = mutableListOf<Int>()

    if (ehDigrafo){
        for (i in 0 until vertices) {
            arestas.map {

                matrizIncid.add(if (i+1 == it.first) {
                    1
                } else if (i+1 == it.second) {
                    -1
                } else {
                    0
                })
            }
        }
    }else{

        for (i in 0 until vertices) {
            arestas.map {

                matrizIncid.add(if (i+1 == it.first) {
                    1
                } else if (i+1 == it.second) {
                    1
                } else {
                    0
                })
            }
        }
    }
    return matrizIncid
}

fun geraTabelaIncid(arestas: List<Triple<Int,Int,Int>>):List<Int>{

    return arestas.map { it.first } + arestas.map { it.second }.toList()

}


//ALGORITMOS

fun Grafo.buscaEmLargura(inicial:Int){
    val listaOrdemVisita = mutableListOf<Int>()//Criação da lista final
    val fronteira = mutableListOf<Int>()//Criação da lista de fronteira
    var verticeAtual = inicial
    var indiceBuscaLinha :Int
    var indiceBuscaColuna :Int
    var colunaIncidencia:Int

        while (listaOrdemVisita.size != vertices) {//condição de parada -> lista final preenchida com quantidade de vértices do grafo

            for (i in 0 until arestas.size) {//itera  $arestas.size vezes (varredura de linha)
                indiceBuscaLinha = (((verticeAtual*arestas.size)-1)-(arestas.size-1))+i

                if (matrizIncid[indiceBuscaLinha] == 1) {//quando achar uma incidencia, buscaremos aonde ela incide na mesma coluna
                    colunaIncidencia = indiceBuscaLinha - (verticeAtual*arestas.size) + arestas.size
                    for (j in 1 ..vertices) {//vasculhando a coluna linha a linha
                       indiceBuscaColuna = (((j*arestas.size)-1)-(arestas.size-1))+colunaIncidencia

                        if (matrizIncid[indiceBuscaColuna] == 1 && j!=verticeAtual) {
                            if (!fronteira.contains(j) && !listaOrdemVisita.contains(j)){//Verifica se vertice encontrado já está na lista de fronteira ou na lista final
                                fronteira.add(j)//adiciona o vértice atual na lista de fronteira
                            }
                            break
                        }
                    }
                }
            }

            listaOrdemVisita.add(verticeAtual) //Atualização da lista final
            if (listaOrdemVisita.size != vertices) {
                verticeAtual = fronteira[0] //vertice atual recebe o primeiro elemento da fronteira
                fronteira.removeAt(0)
                //println("Busca --> $listaOrdemVisita")
                //println("fronteira --> $fronteira")
                //println("Vértice atual --> $verticeAtual")
            }
        }

    println(listaOrdemVisita.toString())
}


fun Grafo.buscaEmProfundidade(inicial:Int){
    val listaOrdemVisita = mutableListOf<Int>()//Criação da lista final
    val fronteira = mutableListOf<Int>()//Criação da lista de fronteira
    var verticeAtual = inicial
    var indiceBuscaLinha :Int
    var indiceBuscaColuna :Int
    var colunaIncidencia:Int

    while (listaOrdemVisita.size != vertices) {//condição de parada -> lista final preenchida com quantidade de vértices do grafo

        for (i in 0 until arestas.size) {//itera  $arestas.size vezes (varredura de linha)
            indiceBuscaLinha = (((verticeAtual*arestas.size)-1)-(arestas.size-1))+i

            if (matrizIncid[indiceBuscaLinha] == 1) {//quando achar uma incidencia, buscaremos aonde ela incide na mesma coluna
                colunaIncidencia = indiceBuscaLinha - (verticeAtual*arestas.size) + arestas.size
                for (j in 1 ..vertices) {//vasculhando a coluna linha a linha
                    indiceBuscaColuna = (((j*arestas.size)-1)-(arestas.size-1))+colunaIncidencia

                    if (matrizIncid[indiceBuscaColuna] == 1 && j!=verticeAtual) {
                        if (!fronteira.contains(j) && !listaOrdemVisita.contains(j)){//Verifica se vertice encontrado já está na lista de fronteira ou na lista final
                            fronteira.add(j)//adiciona o vértice atual na lista de fronteira
                        }
                        break
                    }
                }
            }
        }

        listaOrdemVisita.add(verticeAtual) //Atualização da lista final
        if (listaOrdemVisita.size != vertices) {
            verticeAtual = fronteira[fronteira.lastIndex] //vertice atual recebe o ultimo elemento da fronteira (último a ser adicionado)
            fronteira.removeAt(fronteira.lastIndex)//ultimo elemento da fronteira sai da fronteira
            //println("Busca --> $listaOrdemVisita")
            //println("fronteira --> $fronteira")
            //println("Vértice atual --> $verticeAtual")

        }
    }

    println(listaOrdemVisita.toString())
}

fun Grafo.arvoreGeradoraMinima() {
    val fronteira = mutableListOf<Triple<Int, Int, Int>>()//Criação da lista de fronteira
    val arestasVisitadas = mutableListOf<Triple<Int, Int, Int>>()//Criação da lista de arestas finais
    val verticesVisitados = mutableListOf<Int>()
    var verticeAtual = 1
    var menorValor: Int
    var menorAresta = Triple(-1, -1, -1)//inicialização necessária (valores arbitrários)
    var pesoTotal = 0
    val remover = mutableListOf<Triple<Int, Int, Int>>()

    while (verticesVisitados.size != vertices) {//enquanto nossa arvore não estiver completa não encerramos o algoritmo
        // Atualizações de Fronteira
        verticesVisitados.add(verticeAtual)//adicionamos o vértice atual a arvore

        for (i in 0 until arestas.size) {

            if (verticeAtual == arestas[i].first || verticeAtual == arestas[i].second) {//verificamos quais arestas na lista de arestas estão conectadas com nosso vértice atual
                if (!fronteira.contains(
                        Triple(
                            arestas[i].first,
                            arestas[i].second,
                            arestas[i].third
                        )
                    )
                ) {//caso a aresta encontrada não esteja na fronteira nem seja a ultima menor aresta encontrada.
                    fronteira.add(
                        Triple(
                            arestas[i].first,
                            arestas[i].second,
                            arestas[i].third
                        )
                    )//adicionamos a aresta na fronteira
                }
            }
        }

        //recalculando a fronteira
        fronteira.map {
            if (verticesVisitados.contains(it.first) && verticesVisitados.contains(it.second)) {//removemos arestas que conectam 2 vertices que já estão na arvore da fronteira --> arvore contains it.first && it.second.
                //fronteira.remove(it) -> concurrentModificationException
                // fronteira[i] (for) -> indexOutOfBoundsException
                remover.add(it)
            }
        }
        remover.map { fronteira.remove(it) }


        //Cálculo do menor da fronteira
        menorValor = 999//re-set do menor valor
        for (i in fronteira.indices) {
            if (fronteira[i].third < menorValor) {
                menorValor = fronteira[i].third
                menorAresta = fronteira[i]
            }
        }

        if (!arestasVisitadas.contains(menorAresta)) {
            pesoTotal += menorValor//incrementamos o peso total com o peso da menor aresta da fronteira
            fronteira.remove(menorAresta)
            arestasVisitadas.add(menorAresta)
        }

        //atualizamos o vértice atual para receber o OUTRO componente vertice da menor aresta.
        verticeAtual = if (verticesVisitados.contains(menorAresta.first)) {
            menorAresta.second
        } else {
            menorAresta.first
        }
    }

    println("vértices visitados em ordem de visitação -->  $verticesVisitados")
    println("arestas da árvore geradora mínima em ordem de visitação -->  $arestasVisitadas")
    println("peso total da árvore geradora mínima -->  $pesoTotal")
}

fun Grafo.caminhoMinimo(inicial: Int) {
    //TO-DO
}




 //Testes feitos durante desenvolvimento
/*
fun testandoOperacaoMatrizEmVetor(matriz:List<Int>,teclado:Scanner){
    /*Feito no começo somente com matrizes quadradas em mente.
        As fórmulas corretas são:
        Indice = ((LinhaAtual * Colunas)-1) - (Linhas - ColunaAtual)
        LinhaAtual = (Indice/Colunas)+1
        ColunaAtual = Linhas - (((LinhaAtual * Colunas)-1)-Indice)
     */
    val vertices = matriz[0]
    println("Digite a linha desejada.")
    val linha = teclado.nextInt()
    println("Digite a coluna desejada.")
    val coluna = teclado.nextInt()
    val indice = ((linha*vertices)-1)-(vertices-coluna) //como a matriz é quadrada, o valor do nº de vértices serve como linha e como coluna
    val lrev = (indice/vertices)+1
    val colrev = vertices-(((linha*vertices)-1)-indice)

    val quantEspaco = matriz.maxOrNull()?.toString()?.length ?:1 //Acha o numero de algarismos do maior numero da lista, caso a lista esteja vazia retorna 1 como valor padrão pro espaço de formatação

    for (i in matriz.indices) {//impressão
        val formato = "%${quantEspaco + 1}d "
        print(String.format(formato, matriz[i]))
        if (i % vertices == 0)
            print("\n")
    }
    println("---resultado---")

    println("Elemento da matriz: "+ matriz.drop(1)[indice])
    println("Indice $indice - Vert $vertices |  linha $linha - coluna $coluna | lrev $lrev - colrev $colrev")
}
    */

