
package org.tartarus.snowball;


import org.tartarus.snowball.ext.portugueseStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TestAppPT {
    public static void main(String [] args){

        //text examples
        String text = "Skoda Auto é uma companhia automobilística da República Checa, fundada em 1925, e uma das mais antigas deste ramo no mundo. Em 1991 tornou-se subsidiária do Grupo Volkswagen. Vendeu um total de 939.200 unidades de veículos em 2012. Empresa checa de automóveis, surgiu em 1925 como resultado da fusão da Laurin & Klement, fundada em 1895, e da Skoda Pilsen. A primeira destas empresas já fabricava automóveis, embora tenha começado a sua actividade através da produção de bicicletas. Depois surgiram as motos de corrida e o primeiro automóvel, Voiturette A, um grande sucesso de vendas. Em 1914, com o início da Primeira Guerra Mundial (1914-1918), a empresa passou a dedicar-se ao fabrico de armamento. Em 1925 foi então concretizada a fusão com a maior empresa industrial checa, a Skoda Pilsen, tendo desde logo sido montada uma linha de produção de veículos de luxo.";

        ArrayList<String> tokens = new ArrayList<>();

        Reader reader;
        reader = new StringReader(text);
        reader = new BufferedReader(reader);

        StringBuffer input = new StringBuffer();

        int character;

        // only count letters for tokens, everything else get removed

        try {
            while ((character = reader.read()) != -1) {
                char ch = (char) character;
                if (!Character.isLetter((char) ch)) {
                    if (input.length() > 0) {
                        tokens.add(input.toString());
                        input.delete(0, input.length());
                    }
                } else {
                    input.append(Character.toLowerCase(ch));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--- All tokens ---");
        System.out.println(tokens);
        System.out.println("--- Stop word parsed tokens ---");
        System.out.println(tokens=remStopWords(tokens));
        System.out.println("--- Porter stemmer and Stop word parsed tokens ---");
        System.out.println(tokens=stem(tokens));
    }

    /**
     * Applies Porter stemmer according to the library available here http://snowball.tartarus.org/download.html
     * @return this
     */
    public static ArrayList<String> stem(ArrayList<String> tokens) {
        portugueseStemmer stemmer =  new portugueseStemmer();

        for (int i = 0; i < tokens.size(); i++) {
            stemmer.setCurrent(tokens.get(i).toString());
            stemmer.stem();
            tokens.set(i, stemmer.getCurrent());
        }

        return tokens;
    }

    /**
     * Stop words list, according to this list http://snowball.tartarus.org/algorithms/portuguese/stop.txt
     */
    private final static ArrayList<String> stopWords = new ArrayList<String>(
            Arrays.asList("de","a","o","que","e","do","da","em","um","para","é","com","não","uma","os","no","se","na","por","mais","as","dos","como","mas","foi","ao","ele","das","tem","à","seu","sua","ou","ser","quando","muito","há","nos","já","está","eu","também","só","pelo","pela","até","isso","ela","entre","era","depois","sem","mesmo","aos","ter","seus","quem","nas","me","esse","eles","estão","você","tinha","foram","essa","num","nem","suas","meu","ás","minha","têm","numa","pelos","elas","havia","seja","qual","será","nós","tenho","lhe","deles","essas","esses","pelas","este","fosse","dele","tu","te","vocês","vos","lhes","meus","minhas","teu","tua","teus","tuas","nosso","nossa","nossos","nossas","dela","delas","esta","estes","estas","aquele","aquela","aqueles","aquelas","isto","aquilo","estou","está","estamos","estão","estive","esteve","estivemos","estiveram","estava","estávamos","estavam","estivera","estivéramos","esteja","estejamos","estejam","estivesse","estivéssemos","estivessem","estiver","estivermos","estiverem","hei","há","havemos","hão","houve","houvemos","houveram","houvera","houvéramos","haja","hajamos","hajam","houvesse","houvéssemos","houvessem","houver","houvermos","houverem","houverei","houverá","houveremos","houverão","houveria","houveríamos","houveriam","sou","somos","são","era","éramos","eram","fui","foi","fomos","foram","fora","fóramos","seja","sejamos","sejam","fosse","fóssemos","fossem","for","formos","forem","serei","será","seremos","serão","seria","seríamos","seriam","tenho","tem","temos","têm","tinha","tínhamos","tinham","tive","teve","tivemos","tiveram","tivera","tivéramos","tenha","tenhamos","tenham","tivesse","tivéssemos","tivessem","tiver","tivermos","tiverem","terei","terá","teremos","terão","teria","teríamos","teriam"));

    /**
     * Applies stop words filter.
     * @return
     */
    public static ArrayList<String> remStopWords(ArrayList<String> tokens) {
        for (int i = tokens.size()-1; i >= 0; i--) {
            if (isStopWord(tokens.get(i))) {
                tokens.remove(i);
            }
        }

        return tokens;
    }

    private static boolean isStopWord(String word){
        return stopWords.contains(word);
    }
}
