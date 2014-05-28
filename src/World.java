import java.util.ArrayList;
import java.util.Random;

// === 世界クラス ===
// シングルトンで世界に起こる現象について記述する。
// 世界に存在する生物を管理し、生物等からの死亡・出産MSG等によって処理を行う。
public final class World {
  
  private static final World instance   = new World();              // シングルトンインスタンス
  public  Random             rnd        = new Random();             // 乱数（運命）
  private ArrayList<Animal>  animalList = new ArrayList<Animal>();  // 存在する生物リスト
  private ArrayList<Animal>  deadList   = new ArrayList<Animal>();  // 生物の死亡通知リスト
  private int                year;                                  // 世界歴
  private int                endYear;                               // 終了する年
  private boolean            event;                                 // その年のイベント有無
  
  // コンストラクタ無効化
  private World() {}

  // インスタンス取得（シングルトンなのでnewではなく当メソッドでインスタンスを取得して使用）
  public static World getInstance() {
    return instance;
  }

  // 世界歴と限界時間の設定
  public void setYear(int year, int span){
    this.year = year;
    this.endYear = year + span;
  }

  // 世界の時間を進行
  public void pastTime(){
    this.event = false;                                 // イベントクリア
      
      for (int i = 0 ; i < animalList.size() ; i++){    // 全生物の時間を進行
        animalList.get(i).pastTime();
      }
      for (Animal animal: deadList){                    // 全死亡通知リストの反映
        animalList.remove(animalList.indexOf(animal));
      }
      deadList.clear();
      
    year++;
  }
  // イベント時に世界歴表示
  public void yearDisp(){
    if (this.event == false){
      this.event = true;
      System.out.println("<世界歴"+year+"年>");
    }
  }
  //　生物死亡通知メソッド（Humanから死亡通知）
  public void setDead(Animal animal){
    deadList.add(animal);
  }

  // 生物出産メソッド
  public void setBorn(Animal animal){
    animalList.add(animal);
  }

  // 確率成否判定メソッド
  public boolean successPercent(int num){
    if (num <=rnd.nextInt(100)+1)return true;
    return false;
  }

  // 出会いメソッド
  public Animal matching(Animal cliant){
    ArrayList<Animal> partnerTarget = new ArrayList<Animal>();  // 相手候補
    partnerTarget.clear();
    
    for (Animal target: this.animalList){
      if (target.getLive()==true&&                 // 存命
        target.getClass()==cliant.getClass() &&    // 同じ種族
        target.getSex()!=cliant.getSex())          // 異性
      {
        partnerTarget.add(target);
      }
    }
    if (partnerTarget.isEmpty())return null;       // 対象なし
    
    Animal target = partnerTarget.get(this.rnd.nextInt(partnerTarget.size()));
    return target;                                 // １人選択
  }

  // 世界の終了条件取得
  public boolean getContinueStatus(){
    if (animalList.isEmpty()){
      System.out.println("そして誰もいなくなったのでした・・・。");
      return false;
    }
    if (this.year >= this.endYear){
      System.out.println(this.year+"年、世界は役目を終えたのでした・・・。");
      return false;
    }
    return true;
  }
};