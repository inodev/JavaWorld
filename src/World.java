import java.util.ArrayList;
import java.util.Random;

// === ���E�N���X ===
// �V���O���g���Ő��E�ɋN���錻�ۂɂ��ċL�q����B
// ���E�ɑ��݂��鐶�����Ǘ����A����������̎��S�E�o�YMSG���ɂ���ď������s���B
public final class World {
  
  private static final World instance   = new World();              // �V���O���g���C���X�^���X
  public  Random             rnd        = new Random();             // �����i�^���j
  private ArrayList<Animal>  animalList = new ArrayList<Animal>();  // ���݂��鐶�����X�g
  private ArrayList<Animal>  deadList   = new ArrayList<Animal>();  // �����̎��S�ʒm���X�g
  private int                year;                                  // ���E��
  private int                endYear;                               // �I������N
  private boolean            event;                                 // ���̔N�̃C�x���g�L��
  
  // �R���X�g���N�^������
  private World() {}

  // �C���X�^���X�擾�i�V���O���g���Ȃ̂�new�ł͂Ȃ������\�b�h�ŃC���X�^���X���擾���Ďg�p�j
  public static World getInstance() {
    return instance;
  }

  // ���E���ƌ��E���Ԃ̐ݒ�
  public void setYear(int year, int span){
    this.year = year;
    this.endYear = year + span;
  }

  // ���E�̎��Ԃ�i�s
  public void pastTime(){
    this.event = false;                                 // �C�x���g�N���A
      
      for (int i = 0 ; i < animalList.size() ; i++){    // �S�����̎��Ԃ�i�s
        animalList.get(i).pastTime();
      }
      for (Animal animal: deadList){                    // �S���S�ʒm���X�g�̔��f
        animalList.remove(animalList.indexOf(animal));
      }
      deadList.clear();
      
    year++;
  }
  // �C�x���g���ɐ��E��\��
  public void yearDisp(){
    if (this.event == false){
      this.event = true;
      System.out.println("<���E��"+year+"�N>");
    }
  }
  //�@�������S�ʒm���\�b�h�iHuman���玀�S�ʒm�j
  public void setDead(Animal animal){
    deadList.add(animal);
  }

  // �����o�Y���\�b�h
  public void setBorn(Animal animal){
    animalList.add(animal);
  }

  // �m�����۔��胁�\�b�h
  public boolean successPercent(int num){
    if (num <=rnd.nextInt(100)+1)return true;
    return false;
  }

  // �o����\�b�h
  public Animal matching(Animal cliant){
    ArrayList<Animal> partnerTarget = new ArrayList<Animal>();  // ������
    partnerTarget.clear();
    
    for (Animal target: this.animalList){
      if (target.getLive()==true&&                 // ����
        target.getClass()==cliant.getClass() &&    // �����푰
        target.getSex()!=cliant.getSex())          // �ِ�
      {
        partnerTarget.add(target);
      }
    }
    if (partnerTarget.isEmpty())return null;       // �ΏۂȂ�
    
    Animal target = partnerTarget.get(this.rnd.nextInt(partnerTarget.size()));
    return target;                                 // �P�l�I��
  }

  // ���E�̏I�������擾
  public boolean getContinueStatus(){
    if (animalList.isEmpty()){
      System.out.println("�����ĒN�����Ȃ��Ȃ����̂ł����E�E�E�B");
      return false;
    }
    if (this.year >= this.endYear){
      System.out.println(this.year+"�N�A���E�͖�ڂ��I�����̂ł����E�E�E�B");
      return false;
    }
    return true;
  }
};