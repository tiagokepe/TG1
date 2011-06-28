/* Prototipo de implementacao de map e reduce 
 * Tiago Rodrigo Kepe 
 */

package br.ufpr;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.hadoop.fs.Path ;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class Availability {
    private Date currentDate = new Date();
    private String strDate;
    private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    /* Pega data currente e atribui a uma string */
    strDate = formatador.format(currentDate);

    public static class Map extends MapReduceBase 
      implements Mapper<LongWritable , Text , LongWritable , Text> {

        private LongWritable k = new LongWritable();
        private Text v = new Text();

        /* Implementacao da funcao de mapeamento . */
        public void map( LongWritable key , Text value,
          OutputCollector<LongWritable, Text> output, Reporter reporter
          ) throws IOException {

            /* Separa os campos da linha em um array. */
            String[] fields = value.toString().split("\\s");

            /* Pega o MAC da maquina do primeiro campo da linha. */
            Long machine = new Long (fields[1]);


            /* Verifica se o campo data-contato e data atual */
            if (fields[6].equals(strDate)) {

                /* Emite um par chave/valor intermediario, contendo a data de
                   contato(chave) e regiao como string(valor). */
                k.set(strData);

                /* regiao */
                v.set(fields[5]);

                output.collect(k,v);
            }
        }
    }

    public static class Reduce extends MapReduceBase
      implements Reducer<LongWritable, Text, LongWritable, Text> {

        /* Implementacao da funcao de reducao. */
        public void reduce (LongWritable key , Iterator <Text> values,
          Output Collector <LongWritable, Text> output, Reporter reporter
          ) throws IOException {

            count = new Long(0);
            sumSul = new Long(0);
            sumNorte = new Long(0);
            sumSudeste = new Long(0);
            sumCentroOeste = new Long(0);
            sumNordeste = new Long(0);
            
            /* Percorre os registro do dia atual e classifica o numero de
            contatos por regiao */
            while (values.hasNext()) {

                String region = values.next().toString();
                /* Pega o regiao e data. */

                if (region.equals("Sul"))
                    sumSul++;                

                if (region.equals("Norte"))
                    sumNorte++;                

                if (region.equals("Sudeste"))
                    sumSudeste++;                
                
                if (region.equals("Centro Oeste"))
                    sumCentroOeste++;                

                if (region.equals("Nordeste"))
                    sumNordeste++;                

            }
            
            String sul = new String();
            String norte = new String();
            String sudeste = new String();
            String centroOeste = new String();
            String nordeste = new String();
            
            sul+="Sul";            
            sul+= String.format("% 15d", sumSul);

            norte+="Norte";            
            norte+= String.format("% 15d", sumNorte);

            sudeste+="Sudeste";            
            sudeste+= String.format("% 15d", sumSudeste);

            centroOeste+="Centro Oeste";            
            centroOeste+= String.format("% 15d", sumCentroOeste) ;

            nordeste+="Nordeste";            
            nordeste+= String.format("% 15d", sumNordeste) ;

            /* Emite o Data atual(chave), e total de disponibilidade para cada
               regiao */
            output.collect(key, new Text(sul)) ;
            output.collect(key, new Text(norte)) ;
            output.collect(key, new Text(sudeste)) ;
            output.collect(key, new Text(centroOeste)) ;
            output.collect(key, new Text(nordeste)) ;
        }
    }

    public static void main (String[] args) throws Exception {
        JobConf conf = new JobConf (Availability.class) ;
        conf.setJobName("Availability") ;

        /* Define a quantidade de tarefas de reducao. */
        conf.setNumReduceTasks(1) ;

        /* Define o tipo dos pares chave/valor de saida. */
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(Text.class);

        /* Indica as classes que implementam o mapeamento e reducao. */
        conf.setMapperClass(Map.class);
        conf.setReducerClass(Reduce.class);

        /* Indica o tipo dos arquivos de entrada e saida. */
        conf.setInputFormat(TextInputFormat.class);
        conf.stOutputFormat(TextOutputFormat.class);

        /* Le da linha de comando a origem e destino dos arquivos no HDFS. */
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path (args[1]));

        JobClient.runJob(conf);
    }
}
































