package ms.JCards;

/**
 * Classe ausiliaria, specifica per punteggi gestisce i dati globali relativi al
 * contratto ed al calcolo del punteggio di una mano di bridge. Si è preferita
 * una Classe reale ad una astratta o ad un'Interface perchè molto
 * specializzata.
 * 
 * Per l'imput/output dei dati si segue lo standarda PBN:
 * 
 * [Vulnerable "None"] [Scoring "IMP"] [Declarer "S"] [Contract "5HX"] [Result
 * "9"]
 * 
 * 
 * [Vulnerable "None"] The Vulnerable tag value defines the situation of
 * vulnerability. The following tag values are possible: "None" , "Love" or "-"
 * no vulnerability "NS" North-South vulnerable "EW" East-West vulnerable "All"
 * or "Both" both sides vulnerable In export format the tag values "None" and
 * "All" are applied.
 * 
 * [Scoring "IMP"] This tag gives the used scoring method. It is an essential
 * part of the game since the tactics of the players depend on the scoring
 * method. There are a lot of scoring systems with all kind of variations, refer
 * to Bridge Encyclopedia. New scoring systems evolve for coping with all kind
 * of irregularities, see e.g.:
 * http://www.gallery.uunet.be/hermandw/bridge/hermtd.html. The wealth of
 * scoring systems makes standardisation difficult. Therefore, the specification
 * of the tag value is open ended: only example values are given. The tag value
 * consists of fields separated by semicolons. A field indicates either a basic
 * scoring system or a modifier. Examples of basic scoring systems are: MP
 * MatchPoint scoring MatchPoints identical to 'MP' IMP IMP scoring (since 1962)
 * Cavendish Cavendish scoring Chicago Chicago scoring Rubber Rubber scoring BAM
 * Board-A-Match Instant apply InstantScoreTable Examples of modifiers are:
 * Butler the trick point score is IMPed against the average value of all scores
 * Butler-2 as 'Butler', but the 2 extreme scores are not used in computing the
 * average value Experts the trick point score is IMPed against a datum score
 * determined by experts Cross the trick point score is IMPed against every
 * other trick point score, and summed Cross1 value of 'Cross' , divided by
 * number of scores Cross2 value of 'Cross' , divided by number of comparisons
 * Mean the datum score is based on a (normal) average value Median the datum
 * score is based on the median value MP1 MatchPoints are computed as: the sum
 * of points, constructed by earning 2 points for each lower score, 1 point for
 * each equal score, and 0 points for each higher score. MP2 MatchPoints are
 * computed as: the sum of points, constructed by earning 1 point for each lower
 * score, 0.5 points for each equal score, and 0 points for each higher score.
 * OldMP NO bonus of 100 (Doubled) or 200 (Redoubled) for the fourth and each
 * subsequent undertrick, when not vulnerable Mitchell2 see
 * http://www.gallery.uunet.be/hermandw/bridge/hermtd.html Mitchell3 idem
 * Mitchell4 idem Ascherman idem Bastille idem EMP European MatchPoints IMP_1948
 * IMP scoring used between 1948 and 1960 IMP_1961 IMP scoring revised in 1961
 * 
  Point difference (1/4 mani)    IMPs

      0 -   10         0
     20 -   40         1
     50 -   80         2
     90 -  120         3
    130 -  160         4
    170 -  210         5
    220 -  260         6
    270 -  310         7
    320 -  360         8
    370 -  420         9
    430 -  490        10
    500 -  590        11
    600 -  740        12
    750 -  890        13
    900 - 1090        14
   1100 - 1290        15
   1300 - 1490        16
   1500 - 1740        17
   1750 - 1990        18
   2000 - 2240        19
   2250 - 2490        20
   2500 - 2990        21
   3000 - 3490        22
   3500 - 3990        23
   4000 or more       24

* IMP difference    Victory Points

*    0 -  2            10 - 10
*    3 -  6            11 -  9
*    7 - 11            12 -  8
*   12 - 16            13 -  7
*   17 - 21            14 -  6
*   22 - 27            15 -  5
*   28 - 33            16 -  4
*   34 - 39            17 -  3
*   40 - 46            18 -  2
*   47 - 54            19 -  1
*   55 or more         20 -  0 
   
* High Card           Target
* Points       Not Vul.  Vulnerable
*   20              0          0
*   21             50         50
*   22             70         70
*   23            110        110
*   24            200        290
*   25            300        440
*   26            350        520
*   27            400        600
*   28            430        630
*   29            460        660
*   30            490        690
*   31            600        900
*   32            700       1050
*   33            900       1350
*   34           1000       1500
*   35           1100       1650
*   36           1200       1800
*   37           1300       1950
*   38           1300       1950
*   39           1300       1950
*   40           1300       1950 
* The difference between the target score from the above table and the actual score is then converted to IMPs,
* using the standard IMP table. The total IMP scores over a series of hands are totaled to give an overall result.



 * [Declarer "S"] The Declarer tag value is the direction of the declarer of the
 * contract. The tag value is "W" (West), "N" (North), "E" (East), or "S"
 * (South). The Declarer tag can also cope with the irregularity that the
 * declarer and the dummy are swapped. This may happen when e.g. South is
 * declarer, but by accident East plays the first card and South puts his cards
 * on the table. The tag value becomes a caret (^) followed by the direction of
 * the irregular declarer: "^W", "^N", "^E", resp. "^S". When all 4 players
 * pass, then the tag value is an empty string.
 * 
 * [Contract "5HX"] The Contract tag value can be "Pass" when all players pass,
 * or a 'real' contract defined as: "<k><denomination><risk>" with <k> the
 * number of odd tricks, <k> = 1 .. 7 <denomination> the denomination of the
 * contract, being S (spades), H (Hearts), D (Diamonds), C (Clubs), or NT
 * (NoTrump) <risk> the risk of the contract, being void (undoubled), X
 * (doubled), or XX (redoubled)
 * 
 * [Result "9"] The Result tag value gives the result of the game in number of
 * tricks. The possible tag values are: "<result>" number of tricks won by
 * declarer "EW <result>" number of tricks won by EW "NS <result>" number of
 * tricks won by NS "EW <result> NS <result>" number of tricks won by EW resp.
 * by NS "NS <result> EW <result>" number of tricks won by NS resp. by EW with
 * <result> = 0 .. 13 . The <result> must match the actual number of won tricks.
 * However, the players could accidentally agree on a wrong number of tricks. A
 * caret character ("^") preceding one of the above tag values indicates that
 * the <result> differs from the actual number of won tricks. When all 4 players
 * pass, then the tag value is an empty string. In export format the tag value
 * contains the number of tricks won by declarer. The Result tag normally gives
 * the final result after the play has ended. This is the case when all 52 cards
 * have been played, or when the Play section ends with '*'. The Result tag can
 * also be used to give a partial result. When the play has not ended, then the
 * Result tag indicates the number of won tricks for the completed, played
 * tricks in the play section. Usage of '+' in the play section would make it
 * explicitly clear that the Result tag is based on a partial result.
 * 
 * 
 */

public class BridgeBid {

	final static String ITALIANO = "it";
	public final static String[] STRINGS_IT = { "none", "F", "Q", "C", "P",
		"SA", "!", "!!", "Fiori", "Quadri", "Cuori", "Picche",
		"Senza atout", "contre", "surcontre", "m.i." };

	public final static String[] STRINGS_EN = { "none", "C", "D", "H", "S",
		"NT", "X", "XX", "Clubs", "Diamonds", "Hearts", "Spades",
		"Notrump", "contre", "surcontre", "=" };
	
	public final static String[] STRINGS_FR = { "none", "♣", "♦", "♥", "♠",
		"SA", "X", "XX", "Trèfle", "Carreau", "Coeur", "Pique",
		"Sans Atout", "contre", "surcontre", "=" };
	
 /* index = IMP */
  public final static int[] IMP = 	{10, 40, 80, 120, 160, 210, 260, 310, 360, 420,
    	490, 590, 740, 890, 1090, 1290, 1490, 1740, 1990, 2240, 2490, 2990, 3490, 3990, 1000000 };
 /*   20              0          0
  *   21             50         50
  *   22             70         70
  *   23            110        110
  *   24            200        290
  *   25            300        440
  *   26            350        520
  *   27            400        600
  *   28            430        630
  *   29            460        660
  *   30            490        690
  *   31            600        900
  *   32            700       1050
  *   33            900       1350
  *   34           1000       1500
  *   35           1100       1650
  *   36           1200       1800
  *   37           1300       1950
  *   38           1300       1950
  *   39           1300       1950
  *   40           1300       1950 */
  
  /* index = HCP - 20 */
  public  final static int[] RUSSNV = {0, 50, 70, 110, 200, 300, 350, 400, 430, 460, 490, 600,  700,  900, 1000, 1100, 1200, 1300, 1300, 1300, 1300};
  
  public  final static int[]  RUSSV = {0, 50, 70, 110, 290, 440, 520, 600, 630, 660, 690, 900, 1050, 1350, 1500, 1650, 1800, 1950, 1950, 1950, 1950};
	  
 
	// default
	protected static String[] language = BridgeBid.STRINGS_EN;

	/**
	 * Se "true" i dati sono validi e disponibili, altrimenti non inizializzato
	 * o inizializzato con dati incoerenti.
	 */
	protected boolean statusOK = false;
	/*
	 * vulnerable: indica la/le coppia in zona Codificato bit mapped: 0 ==
	 * "None" , "Love" or "-" no vulnerability 1 == "NS" North-South vulnerable
	 * 2 == "EW" East-West vulnerable 3 == "All" or "Both" both sides vulnerable
	 */

	/**
	 * Se "true" il dichiarante è in zona, altrimenti è in prima. 
	 */
	protected boolean declarer_vulnerable;

	/**
	 * Se "true" il difensore è in zona, altrimenti è in prima. 
	 */
	protected boolean defender_vulnerable;

	/**
	 * Punti della linea (NS, EO) cui appartiene il dichiarante.
	 */
	protected int declarer_points = -1;
	/**
	 * Contract defined as: "<k><denomination><risk>" with <k> the number of odd
	 * tricks, <k> = 1 .. 7 <denomination> the denomination of the contract,
	 * being C (Clubs), D (Diamonds), H (Hearts), S (spades) or NT (NoTrump)
	 * <risk> the risk of the contract, being void (undoubled), X (doubled), or
	 * XX (redoubled)
	 */
	int contract_tricks = 1;
	int contract_suit = 1;

	boolean contract_doubled;

	boolean contract_redoubled;

	/**
	 * number of result tricks = 0..13
	 */
	int result_tricks = 0;

	/**
	 * Constructor base.
	 */
	public BridgeBid() {
	}

	public BridgeBid(String s) {
		setValues(s);
	}

	public int getContractSuit() {
		return this.contract_suit;
	}

	public String getLocalContract() {
		String s = "";
		s = s + this.contract_tricks;
		s = s + BridgeBid.language[this.contract_suit];
		if (this.contract_redoubled) {
			s = s + BridgeBid.language[7];
		} else if (this.contract_doubled) {
			s = s + BridgeBid.language[6];
		}
//		if (this.declarer_vulnerable) {
//			s = s ;
//		} else {
//			s = s + "-nV";
//		}

		return s;
	}

	/**
	 * Gets the Contract Suit as long string.
	 */

	public String getLongContractSuit() {
		if ((this.contract_suit > 0) && (this.contract_suit < 6))
			return BridgeBid.language[this.contract_suit + 7];
		return ("?");
	}

	public String getPBNContract() {
		String s = "";
		s = s + this.contract_tricks;
		s = s + BridgeBid.STRINGS_EN[this.contract_suit];
		if (this.contract_redoubled) {
			s = s + BridgeBid.STRINGS_EN[7];
		} else if (this.contract_doubled) {
			s = s + BridgeBid.STRINGS_EN[6];
		}
		if (this.declarer_vulnerable) {
			s = s + ":2";
		} else {
			s = s + ":1";
		}

		return s;
	}

	/**
	 * Gets the Risk as string.
	 */
	public String getRisk() {
		if (this.contract_redoubled)
			return BridgeBid.language[7];
		if (this.contract_doubled)
			return BridgeBid.language[6];
		return ("");
	}

	/**
	 * Calcola il punteggio della mano, modo torneo
	 * 
	 */

	public int getScore() {
		int score = 0;

		if (this.result_tricks < (6 + this.contract_tricks)) {
			// ====== start down
			int down = (6 + this.contract_tricks) - this.result_tricks;
			assert (down > 0);
			if (this.contract_redoubled) {
				if (this.declarer_vulnerable) {
					// zona surcontrato
					// 400 (prima) 600 (successive)
					score = 400 + 600 * (down - 1);
				} else {
					// prima surcontrato
					// 200 (prima) 400 (seconda e terza) 600 (successive)
					switch (down) {
					case 1:
						score = 200;
						break;
					case 2:
						score = 600;
						break;
					case 3:
						score = 1000;
						break;
					default:
						score = 1000 + 600 * (down - 3);
					}
				}
			} else if (this.contract_doubled) {
				if (this.declarer_vulnerable) {
					// zona contrato
					score = 200 + 300 * (down - 1);
				} else {
					// prima contrato
					// 100 (prima) 200 (seconda e terza) 300 (successive)
					switch (down) {
					case 1:
						score = 100;
						break;
					case 2:
						score = 300;
						break;
					case 3:
						score = 500;
						break;
					default:
						score = 500 + 300 * (down - 3);
					}
				}
			} else {
				if (this.declarer_vulnerable) {
					// zona
					score = 100 * down;
				} else {
					// prima
					score = 50 * down;
				}
			}
			// ============ ends down
			return (-score);
		}
		// ============ start positive
		// prese del contratto
		switch (this.contract_suit) {
		case 1: // Clubs
		case 2: // Diamons
			score = 20 * this.contract_tricks;
			break;
		case 3: // Heart
		case 4: // Spades
			score = 30 * this.contract_tricks;
			break;
		case 5: // NT
			score = 40 + 30 * (this.contract_tricks - 1);
		}
		if (this.contract_doubled) {
			score = score * 2;
		}
		if (this.contract_redoubled) {
			score = score * 4;
		}
		// premi manche e bien-joue'
		if ((score >= 100) & (this.declarer_vulnerable)) {
			score = score + 500;
		}
		if ((score >= 100) & (!this.declarer_vulnerable)) {
			score = score + 300;
		}
		if (score < 100) {
			score = score + 50;
		}
		if (this.contract_doubled) {
			score = score + 50;
		}
		if (this.contract_redoubled) {
			score = score + 100;
		}
		// premi slam
		if ((this.contract_tricks == 6) & (!this.declarer_vulnerable)) {
			score = score + 500;
		}
		if ((this.contract_tricks == 6) & (this.declarer_vulnerable)) {
			score = score + 750;
		}
		if ((this.contract_tricks == 7) & (!this.declarer_vulnerable)) {
			score = score + 1000;
		}
		if ((this.contract_tricks == 7) & (this.declarer_vulnerable)) {
			score = score + 1500;
		}
		// prese in piu
		int plus = this.result_tricks - 6 - this.contract_tricks;
		assert (plus >= 0);
		if ((this.contract_doubled) & (!this.declarer_vulnerable)) {
			score = score + 100 * plus;
		}
		if ((this.contract_doubled) & (this.declarer_vulnerable)) {
			score = score + 200 * plus;
		}
		if ((this.contract_redoubled) & (!this.declarer_vulnerable)) {
			score = score + 200 * plus;
		}
		if ((this.contract_redoubled) & (this.declarer_vulnerable)) {
			score = score + 400 * plus;
		}

		if ((!this.contract_redoubled) & (!this.contract_doubled)) {
			switch (this.contract_suit) {
			case 1:
			case 2:
				score = score + 20 * plus;
				break;
			case 3:
			case 4:
			case 5:
				score = score + 30 * plus;
			}
		}
		return score;
	}

	public int getIMP(int score){
		int points = score > 0 ? score: - score;
		int imp = 0;
		if (points >10)
	    	while (IMP[imp] < points)imp++;
		if (score > 0) return imp;
		return -imp;
	}
	
	public int getRussianIMP(int score){
		if (declarer_points < 0) return 0;
		int p_attesi;
		if (declarer_points < 20) {
			if (this.isDefender_vulnerable()) {
				p_attesi = -RUSSV[40-declarer_points-20];
			}
			else {
				p_attesi = -RUSSNV[40-declarer_points-20];
				
			}
			
		}
		else{
			if (this.isDeclarer_vulnerable()) {
				p_attesi = RUSSV[declarer_points-20];
			}
			else {
				p_attesi = RUSSNV[declarer_points-20];
				
			}
		}
		
		return getIMP(score - p_attesi);
	}
	
	
	public boolean isDeclarer_vulnerable() {
		return this.declarer_vulnerable;
	}
	
	public boolean isDefender_vulnerable() {
		return this.defender_vulnerable;
	}

	public boolean isStatusOK() {
		return this.statusOK;
	}

	public void setContract_doubled(boolean contract_doubled) {
		this.contract_doubled = contract_doubled;
	}

	public void setContract_redoubled(boolean contract_redoubled) {
		this.contract_redoubled = contract_redoubled;
	}

	public void setContract_suit(int contract_suit) {
		this.contract_suit = contract_suit;
	}

	public void setContract_tricks(int contract_tricks) {
		this.contract_tricks = contract_tricks;
	}

	public void setDeclarer_points(int declarer_points) {
		this.declarer_points = declarer_points;
	}

	public void setDefender_vulnerable(boolean defender_vulnerable) {
		this.defender_vulnerable = defender_vulnerable;
	}

	public void setDeclarer_vulnerable(boolean declarer_vulnerable) {
		this.declarer_vulnerable = declarer_vulnerable;
	}

	public static void setLanguage(String[] aLanguage) {
		BridgeBid.language = aLanguage;
	}

	public void setResult_tricks(int result_tricks) {
		this.result_tricks = result_tricks;
	}

	/**
	 * Crea una stringa sintetica con i dati della mano (inglese).
	 * 
	 * @return Una stringa pronta per la stampa.
	 */
	public String toBPNString() {
		String s = getPBNContract();
		if (this.declarer_points > 0) {
			s += ":" + this.declarer_points;
		}
		s += "=" + this.result_tricks;
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		s = s + this.contract_tricks;
		s += BridgeBid.language[this.contract_suit];
		s += getRisk();
		s += " ";
		if ((this.contract_tricks + 6) == this.result_tricks) {
			s += BridgeBid.language[15];
		}
		if ((this.contract_tricks + 6) > this.result_tricks) {
			s += "- " + (this.contract_tricks + 6 - this.result_tricks);
		}
		if ((this.contract_tricks + 6) < this.result_tricks) {
			s += "+ " + (this.result_tricks - this.contract_tricks - 6);
		}
		return s;
	}

	/**
	 * Se "true" i dati sono validi e disponibili, altrimenti non inizializzato
	 * o inizializzato con dati incoerenti.
	 */

	/*
	 * Constructor and setup.
	 * 
	 * @param s definizione di una mano.
	 * 
	 * @post.
	 */

	boolean isContract_doubled() {
		return this.contract_doubled;
	}

	boolean isContract_redoubled() {
		return this.contract_redoubled;
	}

	boolean isDeclared() {
		return (this.contract_tricks != 0);
	}

	boolean isPlayed() {
		return (this.result_tricks != 0);
	}

	/**
	 * Legge tutti i dati necessari al calcolo del punteggio da una stringa
	 * sintetica... Accetta iniziali in italiano (minuscole) o inglese
	 * (maiuscole). <prese><seme>[<contre>][:<zona>[:<punti>]]=<presefatte>
	 * esempio: 2CX:2:24=8 7NT=12 2H!!:2=10
	 * 
	 * maiuscole: inglese (PBN) minuscole: italiano Club Diamond Heart Spades
	 * Notrumps fiori quadri cuori picche senza X|x|! = contre XX|xx|!! =
	 * surcontre
	 * 
	 * sn = MyIO.leggiStr("                      Numero prese (1..7) >> ") +
	 * MyIO.leggiStr("             Seme (f|q|c|p|sa|C|D|H|S|NT) >> ") +
	 * MyIO.leggiStr(" Contrate(!|X|x) o surcontrate (!!|XX|xx) >> "); vuln =
	 * MyIO.leggiStr("        (0) or love (1) or vulnerable (2) >> ");
	 * switch(vuln){ case 1: sn = sn + ":1"; break; case 2: sn = sn + ":2";
	 * break; } point =
	 * MyIO.leggiStr(" ============== (0) or  punti della linea >> "); if (point
	 * > 0) sn = sn + ":" + point; fatte =
	 * MyIO.leggiInt(" ============= Prese totali fatte (0..13) >> "); sn = sn +
	 * "=" + fatte;
	 * 
	 * @param x
	 *            a String
	 */
	void setValues(String x) {
		this.statusOK = false;
		// cleanup
		x = x.toUpperCase().trim();
		x = x.replace(" ", "");

		// set contract_tricks
		// <prese><seme>[<contre>][:<zona>[:<punti>]]=<presefatte>
		this.contract_tricks = 0;
		this.contract_tricks = Integer.valueOf(x.substring(0, 1));
		if ((this.contract_tricks < 1) | (this.contract_tricks > 7))
			return;
		// set contract_suit
		x = x.substring(1);
		// <seme>[<contre>][:<zona>[:<punti>]]=<presefatte>
		this.contract_suit = 0;

		if (x.charAt(0) == BridgeBid.language[1].charAt(0)) {
			this.contract_suit = 1;
		}
		if (x.charAt(0) == BridgeBid.language[2].charAt(0)) {
			this.contract_suit = 2;
		}
		if (x.charAt(0) == BridgeBid.language[3].charAt(0)) {
			this.contract_suit = 3;
		}
		if (x.charAt(0) == BridgeBid.language[4].charAt(0)) {
			this.contract_suit = 4;
		}
		if (x.charAt(0) == BridgeBid.language[5].charAt(0)) {
			this.contract_suit = 5;
		}
		if (this.contract_suit == 0)
			return;
		// trim
		int lx = BridgeBid.language[this.contract_suit].length();
		x = x.substring(lx);

		// [<contre>][:<zona>[:<punti>]]=<presefatte>
		// optional
		if (x.startsWith(BridgeBid.language[7])) {
			this.contract_redoubled = true;
		} else {
			if (x.startsWith(BridgeBid.language[6])) {
				this.contract_doubled = true;
			}
		}
		if (x.indexOf(":") >= 0) {
			x = x.substring(x.indexOf(":") + 1);
		} else {
			x = x.substring(x.indexOf("="));
		}

		// [<zona>[:<punti>]]=<presefatte>
		// set declarer_vulnerable;
		this.declarer_vulnerable = false;
		if (x.substring(0, 2).equals("2:")) {
			this.declarer_vulnerable = true;
		}
		if (x.substring(0, 2).equals("2=")) {
			this.declarer_vulnerable = true;
		}
		this.declarer_points = -1;
		if (x.charAt(1) == ':') {
			x = x.substring(2);
			// set points
			// <punti>=<presefatte>
			int j = x.indexOf('=');
			switch (j) {
			// =<presefatte> error ??
			case 0:

				break;
				// n=<presefatte>
			case 1:
				this.declarer_points = Integer.valueOf(x.substring(0, 1));
				x = x.substring(1);
				break;
				// nn=<presefatte>
			case 2:
				this.declarer_points = Integer.valueOf(x.substring(0, 2));
				x = x.substring(2);
				break;
				// error
			default:
				return;
			}
		} else {
			// <zona>=<presefatte>
			x = x.substring(1);
		}
		// set result
		// =<presefatte>
		if (x.charAt(0) != '=')
			return;
		x = x.substring(1);
		this.result_tricks = -1;
		this.result_tricks = Integer.valueOf(x);
		if ((this.result_tricks < 0) | (this.result_tricks > 13))
			return;

		this.statusOK = true;
	}

}
