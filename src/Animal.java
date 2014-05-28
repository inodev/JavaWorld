enum SEX{
  male,
  female,
};

//=== 動物のクラス ===
// 世界に存在する動物の抽象クラス。
// Human等の動物はこのクラスを継承して形作る。
// 全ての動物が持つ特性や挙動を記述する。
abstract class Animal{
  private boolean live;    // 生存フラグ
  private String  name;    // 名前
  private int     age;     // 年齢
  private SEX     sex;     // 性別
  
  // コンストラクタ
  Animal(){
    this.live   = true;
    this.age    = 0;
  }
  // 時間経過経過メソッド
  public void pastTime(){
    this.event();
    age++;
  }
  abstract protected int event();              // 種族別の時間経過イベント
  
  // アクセッサメソッド
  public boolean  getLive(){return this.live;}            // 生存確認
  public String   getName(){return this.name;}            // 名前取得
  public int    getAge(){return this.age;}                // 年齢取得
  public SEX    getSex(){return this.sex;}                // 性別取得
  protected void   setName(String name){this.name=name;}  // 名前設定
  protected void  setSex(SEX sex){this.sex=sex;}          // 性別設定
  protected void  setDead(){this.live=false;}             // 死亡
  public void    setRevival(){this.live=true;}            // 復活
  abstract public boolean love(Animal animal);            // 種族・性格別の交際条件（好み）
};


