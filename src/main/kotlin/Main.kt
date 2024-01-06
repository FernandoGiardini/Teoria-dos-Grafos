import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.charset.Charset
import java.util.Scanner
import kotlin.system.exitProcess

fun main() {
    var sair=""
    var opcao:String
    val teclado = Scanner(System.`in`)
    val saidaArquivo = PrintStream(FileOutputStream("C:\\Users\\ferna\\IdeaProjects\\untitled\\src\\main\\kotlin\\logSaida.txt"))

    val saidaDupla = object: PrintStream(System.out) {//criando variavel para duplicar a saida pro log de saida e para o terminal
        override fun println(ln:Any?){
            super.println(ln)
            saidaArquivo.println(ln)
        }
        override fun print(x: Any?) {
            super.print(x)
            saidaArquivo.print(x)
        }
    }

    System.setOut(saidaDupla)
    var nome=""
    println("Bem vindo ao operador de grafos, digite o nome do arquivo que contém o grafo desejado")
    do {
        try {
            nome = teclado.nextLine()
            File("C:\\Users\\ferna\\IdeaProjects\\untitled\\src\\main\\kotlin\\RepositorioDeGrafos\\${nome}").readText(
                Charset.defaultCharset()
            )
        } catch (ex: Exception) {
            if (nome.lowercase() == "sair"||nome.lowercase() == "s")
                exitProcess(0)
            println("Parece que o arquivo não foi encontrado, confira a escrita e tente novamente ou digite *sair* para encerrar a aplicação")
            println(ex.message)

        }
    }while (!File("C:\\Users\\ferna\\IdeaProjects\\untitled\\src\\main\\kotlin\\RepositorioDeGrafos\\${nome}").exists() || nome.lowercase() == "sair")

    val meuGrafo = Grafo(nome)

    while (sair != "s"){ //Fluxo de ciclo de vida da aplicação
        println("\nDigite *sair* para sair, ou digite o comando desejado para realizar operações no ${meuGrafo.verificaTipo()}, do arquivo $nome:\n" +
                " *ver*       para exibir o grafo em todos os formatos disponpiveis\n" +
                " *matDist*   para exibir o grafo provido no formato: Matriz de distâncias\n" +
                " *matAdj*    para exibir o grafo provido no formato: Matriz de adjacências\n" +
                " *matIncid*  para exibir o grafo provido no formato: Matriz de incidências\n" +
                " *tabIncid*  para exibir o grafo provido no formato: Tabela de incidências\n")
        when(meuGrafo.verificaTipo()){
            "Grafo simples"-> println(" *larg*      para  busca em largura\n" +
                    " *prof*      para  busca em profundidade\n" +
                    " *eul*       para ciclo euleriano\n")

            "Grafo simples e Valorado"-> println(" *prim*      para  árvore geradora mínima\n" +
                    " *djik*      para caminho mínimo\n")

            "Digrafo simples e Valorado"-> println( " *top*       para ordenação topológica\n")
        }

        opcao = teclado.nextLine()
        when(opcao.lowercase()){
            "sair","s"-> sair = "s"

            "ver"-> { println("\nMatriz de Distâncias:\n")
                        imprimirMatrizQuadrada(meuGrafo.matrizDist)
                      println("\nMatriz de Adjacências:\n")
                        imprimirMatrizQuadrada(meuGrafo.matrizAdj)
                    println("\nMatriz de Incidências:\n")
                        imprimirMatrizMN(meuGrafo.matrizIncid,meuGrafo.arestas.size)
                    println("\nTabela de Incidências:\n")
                        imprimirMatrizMN(meuGrafo.tabelaIncid,meuGrafo.arestas.size)
            }

            "arestas"-> { println("\nArestas:\n") ; meuGrafo.imprimeArestas() }

            "matdist"-> { println("\nMatriz de Distâncias:\n"); imprimirMatrizQuadrada(meuGrafo.matrizDist) }

            "matadj"->{ println("\nMatriz de Adjacências:\n") ; imprimirMatrizQuadrada(meuGrafo.matrizAdj) }

            "matincid"->{ println("\nMatriz de Incidências:\n") ; imprimirMatrizMN(meuGrafo.matrizIncid,meuGrafo.arestas.size) }

            "tabincid"-> { println("\nTabela de Incidências:\n") ; imprimirMatrizMN(meuGrafo.tabelaIncid,meuGrafo.arestas.size) }


            "larg"-> {// { if(meuGrafo.verificaTipo() == "Grafo simples") {

                val inicial = escolheVerticeInicial(teclado,meuGrafo.vertices)
                    println("\nBusca em largura a partir do vértice $inicial:\n")
                    meuGrafo.buscaEmLargura(inicial)}

            //} else  println("\nVocê escolheu busca em largura, mas este tipo de Grafo não comporta essa operação. Escolha novamente.")}



            "prof"-> {
                 //{(meuGrafo.verificaTipo() == "Grafo simples") {
                val inicial = escolheVerticeInicial(teclado, meuGrafo.vertices)
                println("\nBusca em profundidade a partir do vértice $inicial:\n")
                meuGrafo.buscaEmProfundidade(inicial)


            } // } else  println("\nVocê escolheu busca em profundidade, mas este tipo de Grafo não comporta essa operação. Escolha novamente.")}



            "eul"-> { if(meuGrafo.verificaTipo() == "Grafo simples")

                println("\nCiclo euleriano:\n" )//;todo

            else  println("\nVocê escolheu ciclo euleriano, mas este tipo de Grafo não comporta essa operação. Escolha novamente.")}



            "prim"-> { if(meuGrafo.verificaTipo() == "Grafo simples e Valorado") {

                println("\nÁrvore geradora mínima:\n")
                meuGrafo.arvoreGeradoraMinima()

            } else  println("\nVocê escolheu árvore geradora mínima, mas este tipo de Grafo não comporta essa operação. Escolha novamente.")}



            "djik"-> { if(meuGrafo.verificaTipo() == "Grafo simples e Valorado") {
                val inicial = escolheVerticeInicial(teclado, meuGrafo.vertices)
                println("\nCaminho mínimo a partir do vértice $inicial:\n")
                meuGrafo.caminhoMinimo(inicial)


            } else  println("\nVocê escolheu caminho mínimo, mas este tipo de Grafo não comporta essa operação. Escolha novamente.")}



            "top"-> { if(meuGrafo.verificaTipo() == "Digrafo simples e Valorado")

                println("\nOrdenação topológica:\n" )//;todo

            else  println("\nVocê escolheu ordenação topológica, mas este tipo de Grafo não comporta essa operação. Escolha novamente.")}
        }
    }
    saidaDupla.close()
}