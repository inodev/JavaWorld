class Main{
  public static void main(String args[]){
    System.out.println("main method start!!!");
 
    World world = World.getInstance();            // 世界クラスのインスタンス取得
    world.setYear(2014, 300);                     //　開始年, 観察年数設定

    
    world.setBorn(new Human("一郎", SEX.male));    // 親がいない為初期人類は手動設置
    world.setBorn(new Human("ジョン", SEX.male));
    world.setBorn(new Human("庄司", SEX.male));
    world.setBorn(new Human("健太", SEX.male));  
    world.setBorn(new Human("花子", SEX.female));
    world.setBorn(new Human("めぐみ", SEX.female));
    world.setBorn(new Human("キャサリン", SEX.female));
    world.setBorn(new Human("藤子", SEX.female));

    do{
      world.pastTime();                            // メインループによる世界時間の進行
    }while(world.getContinueStatus());
    
    System.out.println("main method end!!!");
  }
}