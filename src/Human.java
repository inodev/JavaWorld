// イベントの種類
enum eventType {
	CHARACTERIZATION,		// 性格形成
	ASSOCIATE,				// 交際
	BREAKUP,				// 別れ
	MARRIAGE,				// 結婚
	DIVORCE,				// 離婚
	CHAILDBORN, 			// 子供誕生
	DEAD,					// 死亡
};

// 性格の種類
enum charaType {
	COOL,
	FUNNY,
	STRAIGHT,
};

// === 人間クラス ===
// 動物クラスの代表。様々なイベントにより他の動物や世界に影響を与える。
public class Human extends Animal{

	World world = World.getInstance();	// 世界クラスインスタンス取得
	private charaType	myChara;		// 自身の性格
	private charaType	unlikeChara;	// 苦手な性格
	private Human		associate;		// 交際相手
	private Human		marriage;		// 結婚相手
	private int 		childenNum;		// 子供の数

	Human(String name, SEX sex){
		this.setName(name);				// 名前設定
		this.setSex(sex);				// 性別設定
		this.myChara	= null;			// 性格初期化
		this.unlikeChara= null;			// 苦手な性格初期化
		this.associate	= null;			// 交際相手初期化
		this.marriage	= null;			// 結婚相手初期化
		this.childenNum	= 0;			// 子供の数初期化

	}
	// アクセッサメソッド
	public charaType getChara(){return this.myChara;}					// 性格取得
	public Human getAssociate(){return this.associate;}					// 交際相手取得
	public Human getMarriage(){return this.marriage;}					// 結婚相手取得
	public void setAssociate(Human partner){this.associate = partner;}	// 交際相手登録
	public void setMarriage(Human partner){this.marriage = partner;}	// 結婚相手登録
	// 付き合い判定メソッド
	public boolean love(Animal animal){
		if ( animal.getClass()==this.getClass()&&
			(animal.getAge() < (this.getAge() + this.getAge()/3))||
			(((Human)animal).getChara()!=this.unlikeChara)){
			return true;
		}
		return false;
	}
	// イベント処理
	public int event(){
		// 時期固定イベント
		switch(this.getAge()){
		case   0:msg("が生まれました。");break;
		case   6:msg("が小学生になりました。");break;
		case  12:msg("が中学生になりました。");break;
		case  15:msg("が高校生になりました。");break;
		case  20:happen(eventType.CHARACTERIZATION,100);break;
		case  60:msg("が還暦を迎えました。");break;
		case 110:happen(eventType.DEAD,100);break;
		default:
		}
		// 時期分散イベント
		switch(this.getAge()/10*10){	// 10代刻みで毎年判定
		case 0:
			happen(eventType.CHARACTERIZATION,1);
			break;
		case 10:
			happen(eventType.CHARACTERIZATION,20);
			happen(eventType.ASSOCIATE,10);
			happen(eventType.BREAKUP,30);
			break;
		case 20:
			happen(eventType.ASSOCIATE,25);
			happen(eventType.BREAKUP,10);
			happen(eventType.MARRIAGE,20);
			happen(eventType.CHAILDBORN,10);
			happen(eventType.DIVORCE,1);
			break;
		case 30:
			happen(eventType.ASSOCIATE,16);
			happen(eventType.BREAKUP,5);
			happen(eventType.MARRIAGE,4);
			happen(eventType.CHAILDBORN,8);
			happen(eventType.DIVORCE,1);
			break;
		case 40:
			happen(eventType.ASSOCIATE,10);
			happen(eventType.BREAKUP,5);
			happen(eventType.MARRIAGE,10);
			happen(eventType.CHAILDBORN,3);
			happen(eventType.DIVORCE,1);
			break;
		case 50:
			happen(eventType.ASSOCIATE,3);
			happen(eventType.BREAKUP,3);
			happen(eventType.MARRIAGE,1);
			happen(eventType.DIVORCE,1);
			break;
		case 60:
			happen(eventType.ASSOCIATE,1);
			happen(eventType.BREAKUP,1);
			happen(eventType.DIVORCE,1);
			happen(eventType.DEAD,1);
			break;
		case 70:
			happen(eventType.DEAD,10);
			break;
		case 80:
			happen(eventType.DEAD,20);
			break;
		case 90:
			happen(eventType.DEAD,40);
			break;
		case 100:
			happen(eventType.DEAD,60);
			break;
		default:
		}
		return 0;
	}

	// 各種イベント処理
	private boolean happen(eventType execEvent, int per){
		// イベント発生確認
		if ( world.successPercent(per) ||	// イベントエンカウント確率
			 badStatus(execEvent))			// 個別発生条件・確率
		{
			return false;
		}

		// 有効イベントの動作
		switch(execEvent){
		case CHARACTERIZATION:	// 性格形成イベント
			// ランダムに性格割り当て
			// =>Javaでは列挙体と数値が互換で使いづらい為、配列化してからランダムな要素で割り当てている。
			charaType[] charalist = charaType.values();
			this.myChara	= charalist[world.rnd.nextInt(charalist.length)];
			this.unlikeChara= charalist[world.rnd.nextInt(charalist.length)];
			msg("は「"+this.myChara.toString()+"」な性格のようです。"
					+ "「"+this.unlikeChara.toString()+"」な人とはあまり相性は良くないようです。");
			break;

		case ASSOCIATE:	// 交際イベント
			Human target = (Human)world.matching(this);
			if (target==null ||						// 対象がいない
				this.love(target)==false)			// 自分の好みではない
			{
				msg("は誰かと付き合いたいと思っているようです。");
			}
			else if (target.getAssociate()!=null)	// 相手が交際中
			{
				msg("は"+target.getName()+"が好きでしたが、既に"+target.getAssociate().getName()+"と付き合っていた為諦めたようです。");
			}
			else if (target.love(this)==false)		// 相手の好みではない
			{
				msg("は"+target.getName()+"（"+target.getAge()+"歳）に告白して玉砕しました。");
			}
			else									// 交際条件クリア
			{
				if (this.marriage!=null ||			// 相手が結婚済
					target.getMarriage()!=null)		// 相手が結婚済
				{
					msg("は"+target.getName()+"（"+target.getAge()+"歳）と不倫中のようです。");
				}else{
					msg("は"+target.getName()+"（"+target.getAge()+"歳）と付き合い始めました。");
				}
				this.associate = target;
				target.setAssociate(this);
			}
			break;

		case BREAKUP:	// 別れイベント
			msg("は"+this.associate.getName()+"（"+this.associate.getAge()+"歳）と別れました。");
			this.associate.setAssociate(null);
			this.associate = null;
			break;

		case MARRIAGE:	// 結婚イベント
			msg("と"+this.associate.getName()+"（"+this.associate.getAge()+"歳）が結婚しました！");
			this.marriage	= this.associate;
			this.associate.setMarriage(this);
			this.associate.setAssociate(null);
			break;

		case DIVORCE:	// 離婚イベント
			msg("が離婚しました・・・。");
			this.marriage.setMarriage(null);			// 相手の結婚相手から自分を破棄
			this.marriage=null;							// 自分の結婚相手から相手を破棄
			break;

		case CHAILDBORN:	// 出産イベント
			SEX babySex; 
			String babyName;
			if (world.rnd.nextInt(2)==1){
				babySex=SEX.male;
				babyName=maleName[world.rnd.nextInt(maleName.length)];
			}else{
				babySex=SEX.female;
				babyName=femaleName[world.rnd.nextInt(femaleName.length)];
			}
			this.childenNum++;
			msg("に"+childenNum+"人目の子供が出来ました。名前は"+babyName+"です。");
			Human baby = new Human(babyName, babySex);
			world.setBorn(baby);						// 出産：世界に子供を追加
			break;

		case DEAD:	// 死亡イベント
			msg("が安らかな眠りにつきました・・・。");
			this.setDead();
		    world.setDead(this);
			break;
		}
		return true;
	}

	// 各種イベント失敗条件
	private boolean badStatus(eventType eventstatus){
		if (this.getLive() == false){		// 死亡フラグで全イベント無効
			return true;
		}
		// イベント別判定
		switch(eventstatus){
		case CHARACTERIZATION:
			if(this.myChara!=null){			// 性格付けイベントは既既に行われていれば無効
				return true;
			}
			break;
		case ASSOCIATE:
			if(this.associate!=null){		// 交際イベントは交際中で無効
				return true;
			}
			if( this.marriage!=null &&		// 結婚済だと確率で不倫
				world.successPercent(10))
			{
				return true;
			}
			break;
			
		case BREAKUP:
			if(this.associate==null){		// 別れイベントは交際無しで無効
				return true;
			}
			break;

		case MARRIAGE:
			if( this.marriage!=null ||		// 結婚イベントは既に結婚済
				this.associate==null)		// または交際相手なしで無効
			{	
				return true;
			}
			break;
		case DIVORCE:
			if(this.marriage==null){ 		// 離婚イベントは未婚で無効
				return true;
			}
		break;
		case CHAILDBORN:					// 出産イベントは
			if( this.getSex()==SEX.male	||	// 男 
				this.marriage==null		||	// 未婚
				this.childenNum >= 5 ){		// 子供５人以上で無効
				return true;
			}
			break;
		case DEAD:
			break;
		}
		return false;
	}
	
	// メッセージ処理
	private void msg(String str){
		if(this.getLive()==true){
			world.yearDisp();
			System.out.println("★"+this.getName() + "（" + this.getAge() + "歳）" + str) ;
		}
	}
	
	//名前のリスト
	private String[] maleName={
		"充",
		"二郎",
		"耕作",
		"勇気",
		"元気",
		"慎二",
		"リチャード",
		"ジョニー",
		"ジャック",
	};
	private String[] femaleName={
		"英子",
		"花子",
		"知世",
		"美香",
		"ゆき",
		"レイ",
		"あい",
		"エリー",
		"恵梨",
	};
};

