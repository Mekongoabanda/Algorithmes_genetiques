public class AlgoGen {


    public static void main(String... args){

        //Création d'une population de Y individus
        Population FirstPopulation = new Population (true);
        System.out.println( " \n \n ************************** CREATION D'UNE POPULATION DE " + FirstPopulation.getSize() + " INDIVIDUS  ****************************************");
        //FirstPopulation.affichePopulation();

        SimpleGeneticAlgorithm geneticAlgorithm = new SimpleGeneticAlgorithm();
        geneticAlgorithm.runAlgorithm(FirstPopulation.getSize(), 100);

    }
}
