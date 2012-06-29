/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuGenetico.jgap_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import rutas.GetRoutes;

/**
 * Sudoku realizado con algorimtos geneticos con el paquete jgap.
 * @author civilian
 */
public class JGAPSudoku {

    private static final int GENERACIONES = 60;
    private static final int POBLACION = 1000;
    private int nn;
    private int n;
    private Scanner sc;
    private Coordenadas c;

    static void dbg(Object... o) {
        System.out.println(Arrays.deepToString(o));
    }

    /**
     * @author civilian
     * @since 1.0
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
//            dbg("hola");
            JGAPSudoku sud = new JGAPSudoku();
            sud.solveSudoku();
        } catch (Exception ex) {
            Logger.getLogger(JGAPSudoku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JGAPSudoku() throws FileNotFoundException, IOException {
        sc = new Scanner(new File(GetRoutes.escogerRutaArchivo()));
        n = sc.nextInt();
//        dbg(n);
        nn = n * n;
        c = new Coordenadas(n);
    }
//    static void writeMatrix(int[][] solution) {
//        for (int i = 0; i < 9; ++i) {
//            if (i % 3 == 0) {
//                System.out.println(" -----------------------");
//            }
//            for (int j = 0; j < 9; ++j) {
//                if (j % 3 == 0) {
//                    System.out.print("| ");
//                }
//                System.out.print(solution[i][j] == 0 ? " " : Integer.toString(solution[i][j]));
//
//                System.out.print(' ');
//            }
//            System.out.println("|");
//        }
//        System.out.println(" -----------------------");
//    }

    public void solveSudoku() throws Exception {

        System.out.println("tamaño lado del cuadrado del sudoku =" + n);

        // Configuracion default
        Configuration conf = new DefaultConfiguration();

        // Se cambia el evaluador de aptitud
        FitnessFunction myFunc = new FuncionAptitudSudoku(n);
        conf.setFitnessFunction(myFunc);

        // Ahora se crea genes de prueba para configurar el problema
        Gene[] sampleGenes = new Gene[nn * nn];
        for (int k = 0; k < sampleGenes.length; k++) {
            sampleGenes[k] = new GenSudoku(conf, 1, nn * nn);
        }

        int val;
        for (int i = 0; i < nn; i++) {
            for (int j = 0; j < nn; j++) {
                val = sc.nextInt();
                if (val != 0) {
//                    sampleGenes[c.campo(i, j)]=new GenSudoku(conf, 1, nn,true,val);
                    ((GenSudoku) sampleGenes[c.campo(i, j)]).setImmutableValue(val);
                }
            }
        }
//        dbg(sampleGenes);
//        dbg(sampleGenes.length);
        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);

        //Cuantos cromosomas en la poblacion
        conf.setPopulationSize(POBLACION);
        Genotype population;
//        try {
//            Document doc = XMLManager.readFile(new File("testJGAP.xml"));
//            population = XMLManager.getGenotypeFromDocument(conf, doc);
//        } catch (FileNotFoundException fex) {
//            population = Genotype.randomInitialGenotype(conf);
//        }
        //estos son los primeros 
        population = Genotype.randomInitialGenotype(conf);

        System.out.print("Evolucionanado ");
//        dbg("populacion",population);
        for (int i = 0; i < GENERACIONES; i++) {
            dbg("evolucion i ", i);
            population.evolve();
            System.out.print(population.getFittestChromosome().getFitnessValue() + " ");
        }
        System.out.println();

        /****
        // Save progress to file. A new run of this example will then be able to
        // resume where it stopped before!
        
        // represent Genotype as tree with elements Chromomes and Genes
        DataTreeBuilder builder = DataTreeBuilder.getInstance();
        IDataCreators doc2 = builder.representGenotypeAsDocument(population);
        
        // create XML document from generated tree
        XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
        Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
        XMLManager.writeFile(xmlDoc, new File("testJGAP.xml"));
         ****/
        // Display the best solution we found.
        // -----------------------------------
        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        System.out.println("The best solution has a fitness value of "
                + bestSolutionSoFar.getFitnessValue());
        System.out.println("Here's the completed Sudoku: ");
        Gene[] genes = bestSolutionSoFar.getGenes();

        int[] sqr = new int[nn * nn];
        for (int i = 0; i < nn * nn; i++) {
            sqr[i] = 0;
        }

        for (int i = 0, j = 0; i < nn * nn; i++, j = ((j + 1) % nn)) {
            
            sqr[(Integer) genes[i].getAllele() - 1] = j + 1;
        }
        for (int k = 0; k < genes.length; k++) {
            System.out.print(sqr[k]+ "\t");
            if ((k + 1) % nn == 0) {
                System.out.println();
            }
        }

//        dbg(bestSolutionSoFar);
    }
}
