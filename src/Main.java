class Main{
  public static void main(String args[]){
    System.out.println("main method start!!!");
 
    World world = World.getInstance();			// ���E�N���X�̃C���X�^���X�擾
    world.setYear(2014, 300);					//�@�J�n�N, �ώ@�N���ݒ�

    
    world.setBorn(new Human("��Y", SEX.male));	// �e�����Ȃ��׏����l�ނ͎蓮�ݒu
    world.setBorn(new Human("�W����", SEX.male));
    world.setBorn(new Human("���i", SEX.male));
    world.setBorn(new Human("����", SEX.male));	
    world.setBorn(new Human("�Ԏq", SEX.female));
    world.setBorn(new Human("�߂���", SEX.female));
    world.setBorn(new Human("�L���T����", SEX.female));
    world.setBorn(new Human("���q", SEX.female));

    do{
    	world.pastTime();				// ���C�����[�v�ɂ�鐢�E���Ԃ̐i�s
    }while(world.getContinueStatus());
    
    System.out.println("main method end!!!");
  }
}