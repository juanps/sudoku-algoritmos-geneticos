package SudokuGenetico.watchmaker;

import java.awt.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.SteadyStateEvolutionEngine;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.swing.AbortControl;
import rutas.GetRoutes;

/**
 * @author 
 */
public class WatchmakerSudoku {

    //private final SelectionStrategy<Object> selectionStrategy = new TournamentSelection(selectionPressure.getNumberGenerator());
    private int POBLACION = 100, GENERACIONES = 60;
    private Scanner sc;
    private int n;
    private int nn;
    private Coordenadas c;
    public TextArea taWatchmaker;

    public WatchmakerSudoku() throws IOException {

        sc = new Scanner(new File(GetRoutes.escogerRutaArchivo()));
        n = sc.nextInt();
//        dbg(n);
        nn = n * n;
        c = new Coordenadas(n);
    }

    public WatchmakerSudoku(TextArea taWatchmaker, String archivo, Integer poblacion, Integer generaciones) throws FileNotFoundException {
        this.taWatchmaker = taWatchmaker;
        sc = new Scanner(new File(archivo));
        n = sc.nextInt();
//        dbg(n);
        nn = n * n;
        c = new Coordenadas(n);
        POBLACION = poblacion;
        GENERACIONES = generaciones;
    }

    public void solveSudoku() {
        println("Ejecucion Solucion Watchmaker http://watchmaker.uncommons.org/ api: http://watchmaker.uncommons.org/api/index.html");
        SudokuFactory cf = new SudokuFactory(n, sc, c);

        List<EvolutionaryOperator<Sudoku>> operadores = new ArrayList<EvolutionaryOperator<Sudoku>>(2);

        operadores.add(new SudokuReproduccion());
        operadores.add(new SudokuMutacion());

        EvolutionaryOperator<Sudoku> pipeline = new EvolutionPipeline<Sudoku>(operadores);

        Random rng = new MersenneTwisterRNG();
        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.7));
//        SelectionStrategy<Object> selection = new TruncationSelection(0.25);
        FitnessEvaluator<Sudoku> fit = new SudokuEvaluador(n);
        EvolutionEngine<Sudoku> engine = new GenerationalEvolutionEngine<Sudoku>(cf,
                pipeline,
                fit,
                selection,
                rng);

//        EvolutionEngine<Sudoku> engine = new SteadyStateEvolutionEngine<Sudoku>(cf,
//                pipeline,
//                fit,
//                selection,
//                2,
//                false,
//                rng);
        
        engine.addEvolutionObserver(new EvolutionObserver<Sudoku>() {

            public void populationUpdate(PopulationData<? extends Sudoku> data) {
                print(String.format("Generacion %d aptitud: %s\n",
                        data.getGenerationNumber(),
                        data.getBestCandidateFitness()));
            }
        });

        int elite = (int) (POBLACION * 0.2);
        Sudoku mejor = engine.evolve(POBLACION, elite, new GenerationCount(GENERACIONES));

        println("La mejor solucion tiene una aptitud de: "
                + fit.getFitness(mejor, null));
        println("Aqui esta el sudoku completo: ");
        Sudoku.Gen[] genes = mejor.cells;
        for (int k = 0; k < genes.length; k++) {
            int repr = genes[k].getValue();
            print(repr + "\t");
            if ((k + 1) % nn == 0) {
                println("");
            }
        }

    }

    private void println(String string) {
        print(string + "\n");
    }

    private void print(String string) {
        System.out.print(string);
        if (taWatchmaker != null) {
            taWatchmaker.append(string);
        }
    }

    public static void main(String[] args) throws IOException {
        WatchmakerSudoku o = new WatchmakerSudoku();
        o.solveSudoku();
    }
}
