import java.util.Random;

public class SimpleGeneticAlgorithm {

    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.025;
    Random random = new Random();
    private static int tournamentSize = 0;
    private static final boolean elitism = true;
    private static byte[] solution;

    public SimpleGeneticAlgorithm() {
    }


    //************************** EXECUTION DE NOTRE CODE **************************************************
    public boolean runAlgorithm(int populationSize, int noGenes ) {
        String solution = Integer.toBinaryString(noGenes);
        SimpleGeneticAlgorithm.solution = new byte[solution.length()];
        if (solution.length() != SimpleGeneticAlgorithm.solution.length) {
            throw new RuntimeException("La solution a besoin d'avoir  " + SimpleGeneticAlgorithm.solution.length + " bytes");
        }
        setSolution(solution);
        Population myPop = new Population( true);

        int generationCount = 1;
        while (myPop.getFittest().getFitness() < getMaxFitness()) {
            System.out.println(" \n ***************** Generation n° " + generationCount + " ---- meilleur gène trouvé: " + myPop.getFittest().getFitness());
            myPop.affichePopulation();
            myPop = evolvePopulation(myPop);
            System.out.println("Taille du tournoi : " + tournamentSize);
            System.out.println("************************");
            generationCount++;
        }
        System.out.println("Solution trouvée!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(myPop.getFittest());
        return true;
    }

    //Dans cette étape, nous devons créer une nouvelle population . Tout d'abord, nous devons sélectionner deux
    // objets individuals parents d'une population, en fonction de leur forme. Veuillez noter
    // qu'il est avantageux de permettre à la meilleure personne de la génération actuelle de se reporter à
    // la suivante, sans modification. Cette stratégie s'appelle un élitisme:
    public Population evolvePopulation(Population pop) {
        int elitismOffset;
        Population newPopulation = new Population(pop.getIndividuals().size(), false);

        if (elitism) {
            newPopulation.getIndividuals().add(0, pop.getFittest());
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        for (int i = elitismOffset; i < pop.getIndividuals().size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.getIndividuals().add(i, newIndiv);
        }

        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    //**************************** CROSS OVER

    private Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        for (int i = 0; i < newSol.getDefaultGeneLength(); i++) {
            if (Math.random() <= uniformRate) {
                newSol.setSingleGene(i, indiv1.getSingleGene(i));
            } else {
                newSol.setSingleGene(i, indiv2.getSingleGene(i));
            }
        }
        return newSol;
    }

    //***************** MUTATION ***************************
    private void mutate(Individual indiv) {
        for (int i = 0; i < indiv.getDefaultGeneLength(); i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setSingleGene(i, gene);
            }
        }
    }

    //******************* SELECTION PAR TOURNOI **************************************************************
    private Individual tournamentSelection(Population pop) {
        tournamentSize = random.nextInt(70);
        Population tournament = new Population(tournamentSize, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getIndividuals().size());
            tournament.getIndividuals().add(i, pop.getIndividual(randomId));
        }
        Individual fittest = tournament.getFittest();
        return fittest;
    }

    //******************* récupérer la fitness **************************************************************
    protected static int getFitness(Individual individual) {
        int fitness = 0;
        for (int i = 0; i < individual.getDefaultGeneLength() && i < solution.length; i++) {
            if (individual.getSingleGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }

    //**************** FITNESS MAXIMUM ******************************************************************
    protected int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }

    protected void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

}