enum SEX{
  male,
  female,
};

//=== �����̃N���X ===
// ���E�ɑ��݂��铮���̒��ۃN���X�B
// Human���̓����͂��̃N���X���p�����Č`���B
// �S�Ă̓������������⋓�����L�q����B
abstract class Animal{
  private boolean live;    // �����t���O
  private String  name;    // ���O
  private int     age;     // �N��
  private SEX     sex;     // ����
  
  // �R���X�g���N�^
  Animal(){
    this.live   = true;
    this.age    = 0;
  }
  // ���Ԍo�ߌo�߃��\�b�h
  public void pastTime(){
    this.event();
    age++;
  }
  abstract protected int event();              // �푰�ʂ̎��Ԍo�߃C�x���g
  
  // �A�N�Z�b�T���\�b�h
  public boolean  getLive(){return this.live;}            // �����m�F
  public String   getName(){return this.name;}            // ���O�擾
  public int    getAge(){return this.age;}                // �N��擾
  public SEX    getSex(){return this.sex;}                // ���ʎ擾
  protected void   setName(String name){this.name=name;}  // ���O�ݒ�
  protected void  setSex(SEX sex){this.sex=sex;}          // ���ʐݒ�
  protected void  setDead(){this.live=false;}             // ���S
  public void    setRevival(){this.live=true;}            // ����
  abstract public boolean love(Animal animal);            // �푰�E���i�ʂ̌��ۏ����i�D�݁j
};


