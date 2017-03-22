package ms.JCards.bridgestats;

import com.google.ads.*;
// import android.annotation.TargetApi;
import android.content.Context;
import android.app.Activity;

import java.util.Locale;

import ms.JCards.BridgeBid;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

// import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
// import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * User interface for BridgeStats 2.0.
 * 
 * 
 * @author Marco Sillano
 * 
 */
/*
 * ************ STATS
 */

// public class MainActivity extends Activity implements OnNavigationListener {
public class MainActivity extends FragmentActivity {

	public static class ScoresFragment extends Fragment {
		public ScoresFragment() {
			super();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_imp, container,
					false);
			return view;
		}
	}

	public static class StatsFragment extends Fragment {
		public StatsFragment() {
			super();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_stats, container,
					false);
			return view;
		}
	}

	private static final int MENUITEM_HCP = 1;
	private static final int MENUITEM_ZAD = 2;
	private static final int MENUITEM_BP = 3;

	private static final int MENUITEM_STAT = 4;
	private static final int MENUITEM_POINTS = 5;

	private static final int MENUITEM_0PLUS = 6;
	private static final int MENUITEM_013 = 7;

	private static Fragment afragment = null;

	// stats
	private float mTableSize;
	private static TableLayout mTable = null;
	private static String handNS;
	private static String res1 = "";
	private static String res2 = "";
	private static int usePoint = 1;

	// *************** scores
	static int prese = 1;
	static int suit = 1;
	static int fatte = 7;
	static int doubled = 0;
	static int declarerHCP = 20;
	private static boolean use013 = false;
	private static boolean fullResult = false;
	private static boolean fullvuln = false;
	static BridgeBid play = null;

	private boolean isScoreActive() {
		// return (findViewById(R.id.TextView01) != null);
	  if ( ScoresFragment.class.isInstance(MainActivity.afragment))
		  return (findViewById(R.id.TextView01) != null);
	  return false;
	}

	private boolean isStatsActive() {
		// return (findViewById(R.id.dataTable) != null);
		if( StatsFragment.class.isInstance(MainActivity.afragment))
			return (findViewById(R.id.dataTable) != null);
		return false;
	}

	// main: Hides buttons
	private void hideButtons() {
		Button button1 = (Button) findViewById(R.id.buttonM1);
		button1.setVisibility(View.INVISIBLE);
		Button button2 = (Button) findViewById(R.id.buttonM2);
		button2.setVisibility(View.INVISIBLE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MainActivity.MENUITEM_POINTS, 3,
				this.getString(R.string.mscores));
		menu.add(Menu.NONE, MainActivity.MENUITEM_STAT, 4,
				this.getString(R.string.mstats));

		SubMenu subMenu1 = menu.addSubMenu(1, Menu.NONE, 1,
				this.getString(R.string.hcards));
		MenuItem setHCP = subMenu1.add(1, MainActivity.MENUITEM_HCP, 1,
				"HCP (4321)");
		MenuItem setZHP = subMenu1.add(1, MainActivity.MENUITEM_ZAD, 2,
				"ZHP (Zar: 6421)");
		MenuItem setBP = subMenu1.add(1, MainActivity.MENUITEM_BP, 3,
				"BP (Banzai: 54321)");
		switch (MainActivity.usePoint) {
		case MENUITEM_HCP:
			setHCP.setChecked(true);
			break;
		case MENUITEM_ZAD:
			setZHP.setChecked(true);
			break;
		case MENUITEM_BP:
			setBP.setChecked(true);
			break;
		}
		subMenu1.setGroupCheckable(1, true, true);

		SubMenu subMenu2 = menu.addSubMenu(2, Menu.NONE, 2,
				this.getString(R.string.fatte));
		MenuItem def2 = subMenu2.add(2, MainActivity.MENUITEM_013, 1,
				"0, 1, .., 12, 13");
		def2.setChecked(MainActivity.use013);
		def2 = subMenu2.add(2, MainActivity.MENUITEM_0PLUS, 2,
				".., -1, 0, +1, +2, ..");
		def2.setChecked(!MainActivity.use013);
		subMenu2.setGroupCheckable(2, true, true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.isChecked()) {
			item.setChecked(false);
		} else {
			item.setChecked(true);
		}
		int old = MainActivity.usePoint;
		switch (item.getItemId()) {
		case MENUITEM_HCP:
			Toast.makeText(this, getString(R.string.useHCP), Toast.LENGTH_SHORT)
					.show();
			MainActivity.usePoint = MainActivity.MENUITEM_HCP;
			if (isScoreActive())
				updatePoints(old);
			initScores();
			break;
		case MENUITEM_ZAD:
			Toast.makeText(this, getString(R.string.useZAD), Toast.LENGTH_SHORT)
					.show();
			MainActivity.usePoint = MainActivity.MENUITEM_ZAD;
			if (isScoreActive())
				updatePoints(old);
			initScores();
			break;
		case MENUITEM_BP:
			Toast.makeText(this, getString(R.string.useBP), Toast.LENGTH_SHORT)
					.show();
			MainActivity.usePoint = MainActivity.MENUITEM_BP;
			if (isScoreActive())
				updatePoints(old);
			initScores();
			break;

		case MENUITEM_STAT:
			goStats(null);
			getSupportFragmentManager().executePendingTransactions();
			break;
		case MENUITEM_POINTS:
			goPoints(null);
			getSupportFragmentManager().executePendingTransactions();
			initScores();
			break;

		case MENUITEM_0PLUS:
			Toast.makeText(this, getString(R.string.use101), Toast.LENGTH_SHORT)
					.show();
			MainActivity.use013 = false;
			initScores();
			break;
		case MENUITEM_013:
			Toast.makeText(this, getString(R.string.use013), Toast.LENGTH_SHORT)
					.show();
			MainActivity.use013 = true;
			initScores();
			break;

		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (isScoreActive()) {
			menu.getItem(1).setVisible(true);
			if (MainActivity.use013)
				menu.getItem(1).getSubMenu().getItem(0).setChecked(true);
			else
				menu.getItem(1).getSubMenu().getItem(1).setChecked(true);

		} else {
			menu.getItem(1).setVisible(false);
		}
		return true;
	}

	// main: callback button
	public void goPoints(View view) {
		hideButtons();
		MainActivity.afragment = new ScoresFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, MainActivity.afragment).commit();
		this.setTitle(this.getString(R.string.scores));
		MainActivity.play = null;
	}

	// main: callback button
	public void goStats(View view) {
		hideButtons();
		MainActivity.afragment = new StatsFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, MainActivity.afragment).commit();
		this.setTitle(this.getString(R.string.stats));
		MainActivity.mTable = null;
	}

	// score: callback button for prese, updates
	public void bid(View view) {
		initScores();
		int theButton = view.getId();
		ToggleButton old = (ToggleButton) findViewById(R.id.ToggleButton01
				+ MainActivity.prese - 1);
		old.setChecked(false);
		ToggleButton newB = (ToggleButton) findViewById(theButton);
		newB.setChecked(true);

		MainActivity.prese = theButton - R.id.ToggleButton01 + 1;
		MainActivity.play.setContract_tricks(MainActivity.prese);
		if (!MainActivity.use013) {
			int delta = getVisibleWon();
			MainActivity.fatte = 6 + MainActivity.prese + delta;
			if (MainActivity.fatte > 13) {
				MainActivity.fatte = 13;
			}
			if (MainActivity.fatte < 0) {
				MainActivity.fatte = 0;
			}
			updateWon();
			updatePartialResult();
		}
		updatePartialResult();
	}

	// score: updates text fatte
	private void updateWon() {
		TextView textView1 = (TextView) findViewById(R.id.textViewY);
		if (MainActivity.use013) {
			textView1.setText("" + MainActivity.fatte);
		} else {
			if ((MainActivity.fatte - MainActivity.prese - 6) <= 0)
				textView1.setText(""
						+ (MainActivity.fatte - MainActivity.prese - 6));
			else
				textView1.setText("+"
						+ (MainActivity.fatte - MainActivity.prese - 6));
		}

	}

	// score: getter for fatte text
	private int getVisibleWon() {
		TextView textView1 = (TextView) findViewById(R.id.textViewY);
		String s = textView1.getText().toString().trim();
		if (s.startsWith("+"))
			return Integer.parseInt(s.substring(1));

		return Integer.parseInt(s);
	}

	// score: updates punti when changed mode
	private void updatePoints(int oldUse) {
		double points = MainActivity.declarerHCP;
		int max = 40;
		switch (oldUse) {
		case MENUITEM_HCP:
			break;
		case MENUITEM_ZAD:
			max = 52;
			break;
		case MENUITEM_BP:
			max = 60;
			break;
		}
		points = (points * 40 / max);
		max = 40;
		switch (MainActivity.usePoint) {
		case MENUITEM_HCP:
			break;
		case MENUITEM_ZAD:
			max = 52;
			break;
		case MENUITEM_BP:
			max = 60;
			break;
		}
		points = (points * max / 40);
		MainActivity.declarerHCP = (int) Math.round(points);
		TextView textView1 = (TextView) findViewById(R.id.textViewX);
		textView1.setText("" + MainActivity.declarerHCP);
		setAdvVul();
	}

	// score: callback for declarer points button -
	public void doD(View view) {
		initScores();
		if (MainActivity.declarerHCP > 0) {
			MainActivity.declarerHCP--;
		}
		TextView textView1 = (TextView) findViewById(R.id.textViewX);
		textView1.setText("" + MainActivity.declarerHCP);
		setAdvVul();
	}

	// score: callback for declarer points button +
	public void doU(View view) {
		initScores();
		int max = 40;
		switch (MainActivity.usePoint) {
		case MENUITEM_HCP:
			break;
		case MENUITEM_ZAD:
			max = 52;
			break;
		case MENUITEM_BP:
			max = 60;
			break;
		}
		if (MainActivity.declarerHCP < max) {
			MainActivity.declarerHCP++;
		}
		TextView textView1 = (TextView) findViewById(R.id.textViewX);
		textView1.setText("" + MainActivity.declarerHCP);
		setAdvVul();
	}

	// score: callback for fatte button -
	public void doPD(View view) {
		initScores();
		if (MainActivity.fatte > 0) {
			MainActivity.fatte--;
		}
		updateWon();
		updatePartialResult();
	}

	// score: callback for fatte button +
	public void doPU(View view) {
		initScores();
		if (MainActivity.fatte < 13) {
			MainActivity.fatte++;
		}
		updateWon();
		updatePartialResult();
	}

	// score: updates view at start
	private void scoresSetup() {
		if (isScoreActive()) {
			TextView TextViewPnt = (TextView) findViewById(R.id.TextViewPnt);
			switch (MainActivity.usePoint) {
			case MENUITEM_HCP:
				TextViewPnt.setText("HCP");
				break;
			case MENUITEM_ZAD:
				TextViewPnt.setText("ZHP");
				break;
			case MENUITEM_BP:
				TextViewPnt.setText(" BP");
				break;
			}
			updateWon();
			setAdvVul();
			updatePartialResult();
		}

	}

	// score: set visible/invisible Adv Vuln button
	private void setAdvVul() {
		int max = 40;
		switch (MainActivity.usePoint) {
		case MENUITEM_HCP:
			break;
		case MENUITEM_ZAD:
			max = 52;
			break;
		case MENUITEM_BP:
			max = 60;
			break;
		}
		ToggleButton toggleButtonX = (ToggleButton) findViewById(R.id.ToggleButtonX);
		if (MainActivity.declarerHCP >= max / 2) {
			toggleButtonX.setVisibility(android.view.View.INVISIBLE);
			toggleButtonX.setChecked(false);
		} else {
			toggleButtonX.setVisibility(android.view.View.VISIBLE);
		}
	}

	// score: test for one-time initialization after start
	private void initScores() {
		if (isScoreActive()) {
			if (MainActivity.play == null) {
				String lang = Locale.getDefault().getLanguage().trim();
				if (lang.equalsIgnoreCase("IT")) {
					BridgeBid.setLanguage(BridgeBid.STRINGS_IT);
					MainActivity.play = new BridgeBid("1F:1=7");
				} else {
					if (lang.equalsIgnoreCase("FR")) {
						BridgeBid.setLanguage(BridgeBid.STRINGS_FR);
						MainActivity.play = new BridgeBid("1♣:1=7");
					} else {
						BridgeBid.setLanguage(BridgeBid.STRINGS_EN);
						MainActivity.play = new BridgeBid("1C:1=7");
					}
				}
				MainActivity.use013 = false;
				MainActivity.fatte = 7;
				MainActivity.suit = 1;
				MainActivity.prese = 1;
				MainActivity.declarerHCP = 20;
				MainActivity.doubled = 0;
				MainActivity.fullResult = false;
				updatePoints(MENUITEM_HCP);
//				updatePartialResult();
			}
			scoresSetup();
			    
		}
	}

	// stats: test for one-time initialization after start
	private void initStats() {
		if (isStatsActive() && (MainActivity.mTable == null)) {
			// mano(null);
			MainActivity.mTable = (TableLayout) findViewById(R.id.dataTable);
			this.mTableSize = getResources().getDimension(R.dimen.table_body_size);
		}
	}

	/**
	 * stats: Callback for go button. Does statistics and draws the screen.
	 * 
	 * @param view
	 */
	public void go(View view) {
		initStats();
		String NSc = MainActivity.handNS.replace("-", "");
		if (NSc.length() < 5) {
			addHeader(this.getString(R.string.ERRmsg1),
					this.getString(R.string.ERRmsg2), R.drawable.header_shape1);
			MainActivity.res1 = "";
			MainActivity.res2 = "";
			return;
		}
		if (NSc.length() > 12)
			return;
		// crea gli oggetti
		ms.JCards.Card52 bridge = new ms.JCards.Card52();
		switch (MainActivity.usePoint) {
		/* dfault HCP */
		case MENUITEM_ZAD:
			bridge.setPoints(ms.JCards.Card52.Card52_ZHPPoints);
			break;
		case MENUITEM_BP:
			bridge.setPoints(ms.JCards.Card52.Card52_54321Points);
			break;
		}

		ms.JCards.IGame theGame = new ms.JCards.BridgeProbs(bridge);
		// calcola e stampa le statistiche
		theGame.runGame(0, NSc);
		MainActivity.res1 = (String) theGame.printGame(1, null);
		MainActivity.res2 = (String) theGame.printGame(2, null);
		showStatList();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isScoreActive()) {
			// MainActivity.use013 = false;
			initScores();
		}
	}

	/**
	 * stats: Callback for all card keys. Updates N-S hand
	 * 
	 * @param view
	 */
	public void mano(View view) {
		getMano();
		TextView textView1 = (TextView) findViewById(R.id.TextView02);
		textView1.setText(MainActivity.handNS);

	}

	// scores: callback
	public void risk(View view) {
		initScores();
		int theButton = view.getId();
		ToggleButton contre = (ToggleButton) findViewById(R.id.ToggleButton16);
		ToggleButton surco = (ToggleButton) findViewById(R.id.ToggleButton17);
		if ((theButton == R.id.ToggleButton16) && (contre.isChecked())) {
			surco.setChecked(false);
			doubled = 1;
			MainActivity.play.setContract_doubled(true);
			MainActivity.play.setContract_redoubled(false);
		}
		if ((theButton == R.id.ToggleButton17) && (surco.isChecked())) {
			contre.setChecked(false);
			doubled = 2;
			MainActivity.play.setContract_doubled(false);
			MainActivity.play.setContract_redoubled(true);
		}
		if (!(contre.isChecked() || surco.isChecked())) {
			doubled = 0;
			MainActivity.play.setContract_doubled(false);
			MainActivity.play.setContract_redoubled(false);
		}
		updatePartialResult();
		;
	}

	private void updatePlayValues() {
		// sets engine values
		switch (MainActivity.usePoint) {
		case MENUITEM_HCP:
			MainActivity.play.setDeclarer_points(MainActivity.declarerHCP);
			break;
		case MENUITEM_ZAD:
			MainActivity.play.setDeclarer_points((int) Math
					.round(MainActivity.declarerHCP * 40.0 / 52));
			break;
		case MENUITEM_BP:
			MainActivity.play.setDeclarer_points((int) Math
					.round(MainActivity.declarerHCP * 40.0 / 60));
		}
		MainActivity.play.setContract_tricks(MainActivity.prese);
		MainActivity.play.setContract_suit(MainActivity.suit);
		MainActivity.play.setResult_tricks(MainActivity.fatte);
		switch (doubled) {
		case 0:
			MainActivity.play.setContract_doubled(false);
			MainActivity.play.setContract_redoubled(false);
			break;
		case 1:
			MainActivity.play.setContract_doubled(true);
			MainActivity.play.setContract_redoubled(false);
			break;
		case 2:
			MainActivity.play.setContract_doubled(false);
			MainActivity.play.setContract_redoubled(true);
		}
		MainActivity.play.setDeclarer_vulnerable(MainActivity.fullvuln);

		ToggleButton xVuln = (ToggleButton) findViewById(R.id.ToggleButtonX);
		if (xVuln.isChecked()) {
			MainActivity.play.setDefender_vulnerable(true);
		} else {
			MainActivity.play.setDefender_vulnerable(false);
		}
	}

	// scores: updates working result fields
	private void updatePartialResult() {
		updatePlayValues();
		MainActivity.fullResult = false;
		showScoreResut(MainActivity.play.getLocalContract() + " : "
				+ MainActivity.fatte, "");
	}

	private void updateFinalResult() {
		updatePlayValues();
		MainActivity.fullResult = true;
		showScoreResut(MainActivity.play.toString()	+ " "
				+ (MainActivity.fullvuln ? getString(R.string.svuln)
						: getString(R.string.snvuln)),
				"" + MainActivity.play.getScore());
	}

	// scores: updates result field
	private void showScoreResut(String txt, String val) {

		TextView textView1 = (TextView) findViewById(R.id.TextView01);
		textView1.setText(txt);
		TextView textView2 = (TextView) findViewById(R.id.TextView02);
		textView2.setText(" " + val);

		if (val.equals("")) {
			TextView textViewP1 = (TextView) findViewById(R.id.TextViewP1);
			textViewP1.setText("");
			TextView textViewP2 = (TextView) findViewById(R.id.TextViewP2);
			textViewP2.setText("");
		} else {
			try {
				TextView textViewP1 = (TextView) findViewById(R.id.TextViewP1);
				textViewP1.setText(" "
						+ MainActivity.play.getIMP(Integer.parseInt(val)));
				TextView textViewP2 = (TextView) findViewById(R.id.TextViewP2);
				textViewP2
						.setText(" "
								+ MainActivity.play.getRussianIMP(Integer
										.parseInt(val)));
			} catch (Exception e) {
				/* nothing to do */
			}

		}

	}

	// scores: callback for suit buttons
	public void suit(View view) {
		initScores();
		int theButton = view.getId();
		ToggleButton oldB = (ToggleButton) findViewById(R.id.ToggleButton11
				+ MainActivity.suit - 1);
		oldB.setChecked(false);

		ToggleButton newB = (ToggleButton) findViewById(theButton);
		newB.setChecked(true);
		MainActivity.suit = theButton - R.id.ToggleButton11 + 1;
		MainActivity.play.setContract_suit(MainActivity.suit);
		updatePartialResult();
		;
	}

	// scores: callback for love button
	public void updateNV(View view) {
		initScores();
		MainActivity.fullvuln = false;
		updateFinalResult();
	}

	// scores: callback for vulnerable button
	public void updateV(View view) {
		initScores();
		MainActivity.fullvuln = true;
		updateFinalResult();
	}

	private void addHeader(String dis, String perc, int idRes) {
		TableRow row = new TableRow(this);
		TextView col1 = makeColumnL(dis);
		TextView col2 = makeColumnR(perc);
		row.setBackgroundResource(idRes);
		col1.setTextSize(getResources().getDimension(R.dimen.text_medium));
		// col1.setWidth(140);
		col1.setGravity(Gravity.CENTER);
		col2.setTextSize(getResources().getDimension(R.dimen.text_medium));
		// col2.setWidth(160);
		col2.setGravity(Gravity.CENTER);
		row.addView(col1);
		row.addView(col2);
		MainActivity.mTable.addView(row);
	}

	private void addRow(String dis, String perc) {
		TableRow row = new TableRow(this);
		row.setBackgroundColor(0xFFFFFF);
		row.setBackgroundResource(R.drawable.cell_shape);
		row.addView(makeColumnL(dis));
		row.addView(makeColumnR(perc));
		MainActivity.mTable.addView(row);
	}

	private void getMano() {
		MainActivity.handNS = "";
		for (int i = R.id.ToggleButton21; i <= R.id.ToggleButton27; i++) {
			ToggleButton test1 = (ToggleButton) findViewById(i);
			if (test1.isChecked()) {
				MainActivity.handNS += test1.getText();
			} else {
				MainActivity.handNS += "-";
			}
		}
		for (int j = R.id.ToggleButton28; j <= R.id.ToggleButton33; j++) {
			ToggleButton test2 = (ToggleButton) findViewById(j);
			if (test2.isChecked()) {
				MainActivity.handNS += test2.getText();
			} else {
				MainActivity.handNS += "-";
			}
		}
	}

	private TextView makeColumnL(String text) {
		TextView col = new TextView(this);
		col.setBackgroundColor(0xFFFFFF);
		col.setGravity(Gravity.LEFT);
		col.setTextSize(this.mTableSize);
		col.setText(" " + text);
		return (col);
	}

	private TextView makeColumnR(String text) {
		TextView col = new TextView(this);
		col.setBackgroundColor(0xFFFFFF);
		col.setGravity(Gravity.RIGHT);
		col.setTextSize(this.mTableSize);
		col.setText("  " + text + "  ");
		return (col);
	}

	private void showStatList() {
		// clean list
		if (MainActivity.res1.length() > 10) {
			MainActivity.mTable.removeAllViews();
			addHeader(this.getString(R.string.NScard), MainActivity.handNS,
					R.drawable.header_shape2);
			addHeader(this.getString(R.string.EWcard),
					this.getString(R.string.statistics),
					R.drawable.header_shape1);
			String[] parts = MainActivity.res1.split("(?md)$");
			for (int i = 0; i < parts.length; i++) {
				String[] fields = parts[i].trim().split("=");

				if (fields.length == 2) {
					addRow(" "
							+ fields[0].replace("cards",
									this.getString(R.string.listcard)),
							fields[1]);
				}
			}

			addHeader(this.getString(R.string.Ecard),
					this.getString(R.string.statistics),
					R.drawable.header_shape1);
			parts = MainActivity.res2.split("(?md)$");

			for (int i = 0; i < parts.length; i++) {
				String[] fields = parts[i].trim().split("=");

				if (fields.length == 2) {
					if (fields[0].startsWith("EAST")) {
						String data = fields[0].substring(5).trim();
						switch (MainActivity.usePoint) {
						case MENUITEM_HCP:
							addHeader(this.getString(R.string.east) + " "
									+ data + " HCP", fields[1],
									R.drawable.header_shape2);
							break;
						case MENUITEM_ZAD:
							addHeader(this.getString(R.string.east) + " "
									+ data + " ZHP", fields[1],
									R.drawable.header_shape2);
							break;
						case MENUITEM_BP:
							addHeader(this.getString(R.string.east) + " "
									+ data + " BP", fields[1],
									R.drawable.header_shape2);

						}
					} else {
						addRow(" " + fields[0], fields[1]);
					}
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MainActivity.use013 = false;
		MainActivity.fatte = 7;
		MainActivity.suit = 1;
		MainActivity.prese = 1;
		MainActivity.declarerHCP = 20;
		MainActivity.doubled = 0;
		MainActivity.fullResult = false;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		MainActivity.handNS = savedInstanceState.getString("handNS");
		MainActivity.res1 = savedInstanceState.getString("res1");
		MainActivity.res2 = savedInstanceState.getString("res2");
		MainActivity.usePoint = savedInstanceState.getInt("zad");

		MainActivity.use013 = savedInstanceState.getBoolean("use013");
		MainActivity.declarerHCP = savedInstanceState.getInt("declarerHCP");
		MainActivity.fatte = savedInstanceState.getInt("fatte");
		MainActivity.prese = savedInstanceState.getInt("prese");
		MainActivity.suit = savedInstanceState.getInt("suit");

		if (savedInstanceState.getBoolean("modestat")) {

			// goStats(null);
			// getSupportFragmentManager().executePendingTransactions();
			hideButtons();
			this.setTitle(this.getString(R.string.stats));
			MainActivity.mTable = null;
			initStats();
			mano(null);
			if (MainActivity.handNS.equals("-------------")) {

			} else {
				showStatList();
			}

		}

		if (savedInstanceState.getBoolean("modescore")) {
			/*
			 * goPoints(null);
			 * getSupportFragmentManager().executePendingTransactions();
			 * initScores();
			 */
			hideButtons();
			this.setTitle(this.getString(R.string.scores));
			MainActivity.play = null;
			initScores();

			MainActivity.fullvuln = savedInstanceState.getBoolean("fullvuln");
			MainActivity.fullResult = savedInstanceState
					.getBoolean("fullResult");
			MainActivity.usePoint = savedInstanceState.getInt("zad");
			MainActivity.use013 = savedInstanceState.getBoolean("use013");
			MainActivity.declarerHCP = savedInstanceState.getInt("declarerHCP");
			MainActivity.fatte = savedInstanceState.getInt("fatte");
			MainActivity.prese = savedInstanceState.getInt("prese");
			MainActivity.suit = savedInstanceState.getInt("suit");
			MainActivity.doubled = savedInstanceState.getInt("doubled");

			ToggleButton xbVuln = (ToggleButton) findViewById(R.id.ToggleButtonX);
			xbVuln.setChecked(savedInstanceState.getBoolean("xVuln"));

			MainActivity.play.setContract_suit(MainActivity.suit);
			scoresSetup();
			updatePoints(MainActivity.usePoint);
			updateWon();
			if (savedInstanceState.getBoolean("fullResult"))
				updateFinalResult();
			else
				updatePartialResult();

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		/*
		 * static int prese = 1; static int suit = 1; static int fatte = 0;
		 * static int declarerHCP = 20; private static boolean use013 = false;
		 */
		outState.putBoolean("modestat", isStatsActive());
		outState.putBoolean("modescore", isScoreActive());
		outState.putString("handNS", MainActivity.handNS);
		outState.putString("res1", MainActivity.res1);
		outState.putString("res2", MainActivity.res2);
		outState.putInt("zad", MainActivity.usePoint);
		//
		outState.putBoolean("fullResult", MainActivity.fullResult);
		outState.putBoolean("fullvuln", MainActivity.fullvuln);
		outState.putBoolean("use013", MainActivity.use013);
		outState.putInt("declarerHCP", MainActivity.declarerHCP);
		outState.putInt("fatte", MainActivity.fatte);
		outState.putInt("suit", MainActivity.suit);
		outState.putInt("prese", MainActivity.prese);
		outState.putInt("doubled", MainActivity.doubled);
		if (isScoreActive()) {
			ToggleButton xbVuln = (ToggleButton) findViewById(R.id.ToggleButtonX);
			outState.putBoolean("xVuln", xbVuln.isChecked());
		}
	}

}
