import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuitarHeroTest {

	private static final double EPSILON = 1E-12;

	@ParameterizedTest
	@ValueSource(ints = {-1,0,44100,Integer.MAX_VALUE})
	void testConstructorFrequencyRange(int frequency) {
		assertThrows(IllegalArgumentException.class, () -> {
			new GuitarString(frequency);
		});
	}

	@Test
	void testConstructorArraySize() {
		assertThrows(IllegalArgumentException.class, () -> {
			new GuitarString(new double[]{1.0});
		});
	}

	@ParameterizedTest
	@MethodSource
	void testGuitarStringArray(double[] data, double[] expected) {
		GuitarString g = new GuitarString(data);
		for (int time = 0; time < 10 * data.length; time++) {
			double sample = expected[time];
			double sample2 = g.sample();
			assertTrue(Math.abs(sample - sample2) <= EPSILON);
			g.tic();
		}
	}

	@Test
	void testTimeMethod() {
		Guitar37 guitar37 = new Guitar37();
		int time = guitar37.time();
		assertNotEquals(-1, time);
	}

	@ParameterizedTest
	@ValueSource(chars = {'a','1','|'})
	void testGuitar37Pluck(char key) {
		Guitar37 g = new Guitar37();
		assertThrows(IllegalArgumentException.class, () -> {
			g.pluck(key);
		});
	}

	@ParameterizedTest
	@MethodSource
	void testGuitarStringFrequency(int frequency, int size) {
		GuitarString g = new GuitarString(frequency);
		g.pluck();
		double sum1 = 0.0;
		double first = g.sample();
		Set<Double> values = new HashSet<>();
		for (int j = 0; j < size - 1; j++) {
			double n = g.sample();
			sum1 = sum1 + n;
			values.add(n);
			assertTrue(0.5 > n && n > - 0.5);
			g.tic();
		}
		assertTrue(values.size() > 0.95 * (size - 1));
		double last = g.sample();
		sum1 = sum1 + last;
		g.tic();
		double sum2 = 0.0;
		for (int j = 0; j < size - 1; j++) {
			sum2 = sum2 + g.sample();
			g.tic();
		}
		double correctSum = (sum1 - first / 2.0 - last / 2.0) * 0.996;
		assertTrue(Math.abs(sum2 - correctSum) < EPSILON);
	}

	private static Stream<Arguments> testGuitarStringArray() {
		return Stream.of(
				Arguments.of(
						new double[] {0.0, 0.25, 0.5, 0.375},
						new double[] {0.0,0.25,0.5,0.375,0.1245,0.3735,0.43574999999999997,0.248751,0.248004,0.4030065,0.340881498,0.24738399,0.324203229,0.370456223004,0.292956213024,0.284650435062,0.34594040709799195,0.33037939314194403,0.287648110746828,0.314034239395676,0.3368072605194881,0.30777769693660845,0.299637810370967,0.3241190669577517,0.3210033088131361,0.30249292263917255,0.31063092490970196,0.32127094313390214,0.31050112326324975,0.3053356760793395,0.3146871302857149,0.31462248906578166,0.30668672607260944,0.30877135756979707,0.31339619043704525,0.30941198913891876,0.30649812565391843,0.30983943890740745,0.3101584734288301,0.3067232371668329}
				),
				Arguments.of(
						new double[] {0.125,0.25,0.5,0.375,-0.25,0.5,0.25,0.125,-0.125,-0.375},
						new double[] {0.125,0.25,0.5,0.375,-0.25,0.5,0.25,0.125,-0.125,-0.375,0.18675,0.3735,0.43574999999999997,0.06225,0.1245,0.3735,0.18675,0.0,-0.249,-0.0937485,0.2790045,0.4030065,0.248004,0.0930015,0.248004,0.2790045,0.0930015,-0.124002,-0.170688753,0.09225748799999998,0.33964147799999994,0.324203229,0.169820739,0.169820739,0.262450233,0.185258988,-0.015438249,-0.14675599499400002,-0.039058769970000004,0.21508568506799997,0.33059466408599997,0.246023936064,0.169141456044,0.21527094405599997,0.222959192058,0.08457072802200001,-0.080772733509012,-0.092535752952072,0.08766140371880397,0.27174881387869193,0.28715606287469997,0.206752365269784,0.1914373752498,0.21823860778477197,0.15314990019984,0.00189140126746803,-0.08630762625761983,-0.002427425918167479,0.17898628836355296,0.2783346286231892,0.24596639721595304,0.19829849077875283,0.20401863955121685,0.18495147697633676,0.0772105681307194,-0.04203928004509559,-0.04419005598354208,0.08792631349780197,0.22774581665939758,0.26110191086789286,0.22124391422136352,0.2003539309043249,0.19370711803072171,0.13055669846331397,0.017515301466640657,-0.042942209342261554,0.021780656242101425,0.15720472081828538,0.24344616830859064,0.24020822089444968,0.20995572687259284,0.1962424023696532,0.16148338061402978,0.0737398559651174,-0.012662600122059206,-0.010538453443879745,0.08913471777607263,0.19952414278518427,0.24085988582311407,0.22418164598798715,0.20228666836263853,0.17814743992587412,0.1171411718164153,0.03041647340984298,-0.011554124675837598,0.03914093963743206,0.14375211255950593,0.21931124624693257,0.23159068284192838,0.2123812205466116}
				),
				Arguments.of(
						new double[] {0.125,0.25,0.5,0.375,-0.25,0.5,0.25,0.0,-0.125,-0.375},
						new double[] {0.125,0.25,0.5,0.375,-0.25,0.5,0.25,0.0,-0.125,-0.375,0.18675,0.3735,0.43574999999999997,0.06225,0.1245,0.3735,0.1245,-0.06225,-0.249,-0.0937485,0.2790045,0.4030065,0.248004,0.0930015,0.248004,0.248004,0.0310005,-0.15500250000000002,-0.170688753,0.09225748799999998,0.33964147799999994,0.324203229,0.169820739,0.169820739,0.247011984,0.138944241,-0.061752996000000004,-0.162194243994,-0.039058769970000004,0.21508568506799997,0.33059466408599997,0.246023936064,0.169141456044,0.20758269605399998,0.19220620005,0.038441240009999995,-0.11152572551701201,-0.10022400095407201,0.08766140371880397,0.27174881387869193,0.28715606287469997,0.206752365269784,0.187608627744804,0.199094870259792,0.11486242514987999,-0.03639607378249199,-0.10545136378259985,-0.006256173423163482,0.17898628836355296,0.2783346286231892,0.24596639721595304,0.19639177452126483,0.19257834200628882,0.15635073311401665,0.03907624298095922,-0.07064002390741574,-0.055630353528470136,0.08601959724031397,0.22774581665939758,0.26110191086789286,0.2202943695251345,0.19370711803072171,0.1737666794099121,0.09732263409529798,-0.015718762901375343,-0.06288264796307116,0.015133843368498228,0.15625517612205636,0.24344616830859064,0.23973534763572765,0.2061727408028164,0.18300195112543563,0.13500247812559463,0.040638727854573474,-0.03914350261049436,-0.023778904688097322,0.08535173170629619,0.19905126952646218,0.24062439494027052,0.22206222804239492,0.1938089965802695,0.15836620576701305,0.08746932057812372,7.446221715513988E-4,-0.03133535883469866,0.030663267855063035,0.14163269461391367,0.21895848090443287,0.23041793824536738,0.2071038698620869}
				),
				Arguments.of(
						new double[] {0.0,0.0,0.0,0.75,-0.5,0.0,0.0,0.25},
						new double[] {0.0,0.0,0.0,0.75,-0.5,0.0,0.0,0.25,0.0,0.0,0.3735,0.1245,-0.249,0.0,0.1245,0.1245,0.0,0.186003,0.248004,-0.062001,-0.124002,0.062001,0.124002,0.062001,0.092629494,0.21613548600000002,0.092629494,-0.092629494,-0.030876498,0.092629494,0.092629494,0.077005986012,0.15376496004,0.15376496004,0.0,-0.06150598401600001,0.030752992008000003,0.092258976024,0.08447846904597602,0.114923931133896,0.15314990019984,0.07657495009992,-0.030629980039968002,-0.015314990019984001,0.061259960079936004,0.08801524764484805,0.09930239528957627,0.13350076800420055,0.11440297544928049,0.0228805950898561,-0.0228805950898561,0.0228805950898561,0.07433905344694246,0.0932841861813433,0.11593597532030085,0.12345606423983356,0.06836721812849002,0.0,0.0,0.04841538497132568,0.0834763733348863,0.1041916404278188,0.11921723570094693,0.09552799461942514,0.03404687462798803,0.0,0.02411086171572019,0.06568209563649356,0.09345867085382713,0.11125762031212533,0.1069431246995453,0.06452828488521177,0.01695534356473804,0.012007209134428654,0.04471689276140245,0.07925210171217971,0.10194871300064433,0.10866397101581197,0.08539276197320902,0.040578846968075004}
				)
		);
	}

	private static Stream<Arguments> testGuitarStringFrequency() {
		return Stream.of(
				Arguments.of(11025, 4),
				Arguments.of(4411, 10),
				Arguments.of(4410, 10),
				Arguments.of(4409, 10),
				Arguments.of(4201, 10),
				Arguments.of(4199, 11),
				Arguments.of(441, 100),
				Arguments.of(440, 100),
				Arguments.of(438, 101),
				Arguments.of(123, 359)
		);
	}

}
