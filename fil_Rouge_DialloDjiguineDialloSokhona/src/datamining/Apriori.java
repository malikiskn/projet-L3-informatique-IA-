package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import representation.BooleanVariable;

/**
 * Cette clase représente des extracteurs fonctionnant sur le principe de l'algorithme d'apriori
 */
public class Apriori extends AbstractItemsetMiner{

	/**
	 * Construit une instance 
	 * @param base est represente la base de données 
	 */
	public Apriori(BooleanDatabase base) {
		super(base);
	}

	@Override
	public BooleanDatabase getDatabase() {
		return super.getBase();
	}

	/**
	 * Cette méthode permet de construire un ensemble d'itemset singletons (avec leur frequence)
	 * dont la frequence dans la base est au moins égale à celle donnée.
	 * @param frequenceMin est la frequence minimale
	 * @return la liste des itemsets resultante
	 */
	public Set<Itemset> frequentSingletons(float frequenceMin){
		Set<Itemset> listItemset = new HashSet<>();
		for(BooleanVariable item: getDatabase().getItems()) {
			Set<BooleanVariable> items = new HashSet<>();
			items.add(item);
			float frequenceItem = super.frequency(items);
			if(frequenceItem >= frequenceMin ) {
				listItemset.add(new Itemset(items,frequenceItem));
			}
		}
		return  listItemset;
	}

	/**
	 * Cette méthode prend deux ensembles d'items tous triés et retourne un ensemble d'items trié obténu par combinaison des
	 * deux premiers sur les conditions
	 * les deux ensembles aient meme taille k
	 * les deux ensembles aient les mêmes k − 1 premiers items
	 * es deux ensembles aient des kes items différents.
	 * @param items1 représente le premier ensemble d'items trié
	 * @param items2 représente le premier ensemble d'items trié
	 * @return une liste d'items triée resultante
	 */
	public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> items1,SortedSet<BooleanVariable> items2){
		SortedSet<BooleanVariable> sortedItemsFound=new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
		if(items1.isEmpty() || items2.isEmpty()) {
			return null;
		}
		if((items1.size()==items2.size()) && items1.headSet(items1.last()).equals(items2.headSet(items2.last())) && 
			(!items1.last().equals(items2.last()))){
			sortedItemsFound.addAll(items1);
			sortedItemsFound.add(items2.last());
			return sortedItemsFound;
		}
		return null;
	}
	
	/**
	 * Cette méthode prend un ensemble d'items(de taille k) et une collection constituée d'items frequents et retourne
	 * true si tous les sous-ensembles obtenus en supprimant exactement un élément
	 * de l’ensemble d’items sont contenus dans la collection et false sinon.
	 * @param items représente l'ensemble d'items de taille k
	 * @param frequentItems  reprsente la collection des items frequents
	 * @return true ou false selon que les sous ensembles des premiers sont contenus ou non dans la collection
	 */
	public static boolean allSubsetsFrequent(Set<BooleanVariable> items,Collection<SortedSet<BooleanVariable>> frequentItems) {
		for(BooleanVariable variable:items) {
			Set<BooleanVariable> tmp=new HashSet<>(items);
			tmp.remove(variable);
			if(!frequentItems.contains(tmp)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Set<Itemset> extract(float frequenceMinimale) {
		Set<Itemset> result=new HashSet<>();
		ArrayList<Itemset> res=new ArrayList<>();
		Set<Itemset> frequentItemSize1=this.frequentSingletons(frequenceMinimale);
		result.addAll(frequentItemSize1);
		res.addAll(frequentItemSize1);
		//recupérations des motifs frequent de taille n
		List<SortedSet<BooleanVariable>> itemSetTrace=new ArrayList<>();
		for(Itemset itemSet:frequentItemSize1 ) {
			SortedSet<BooleanVariable> itemSize1=new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
			itemSize1.addAll(itemSet.getItems());
			itemSetTrace.add(itemSize1);
		}
		
		while(!itemSetTrace.isEmpty()) {
			List<SortedSet<BooleanVariable>> itemSetTrace2=new ArrayList<>();
			for(int i=0; i < itemSetTrace.size();i++) {
				for(int j=i+1; j < itemSetTrace.size();j++) {
					SortedSet<BooleanVariable> itemCombine=Apriori.combine(itemSetTrace.get(i),itemSetTrace.get(j) ); //combinaison
					if(itemCombine!=null && Apriori.allSubsetsFrequent(itemCombine, itemSetTrace)) { //verifie si le sous ensemble est frequent
						if(this.frequency(itemCombine) >= frequenceMinimale) {
							itemSetTrace2.add(itemCombine);
							Itemset anItemSet=new Itemset(itemCombine,this.frequency(itemCombine));
							result.add(anItemSet); //on a trouvé un itemset et on l'ajoute
						}
					}
				}
			}
			itemSetTrace=itemSetTrace2; //on au niveau suivant : n+1
		}
		return result;
	}
}
