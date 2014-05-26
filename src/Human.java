// �C�x���g�̎��
enum eventType {
	CHARACTERIZATION,		// ���i�`��
	ASSOCIATE,				// ����
	BREAKUP,				// �ʂ�
	MARRIAGE,				// ����
	DIVORCE,				// ����
	CHAILDBORN, 			// �q���a��
	DEAD,					// ���S
};

// ���i�̎��
enum charaType {
	COOL,
	FUNNY,
	STRAIGHT,
};

// === �l�ԃN���X ===
// �����N���X�̑�\�B�l�X�ȃC�x���g�ɂ�葼�̓����␢�E�ɉe����^����B
public class Human extends Animal{

	World world = World.getInstance();	// ���E�N���X�C���X�^���X�擾
	private charaType	myChara;		// ���g�̐��i
	private charaType	unlikeChara;	// ���Ȑ��i
	private Human		associate;		// ���ۑ���
	private Human		marriage;		// ��������
	private int 		childenNum;		// �q���̐�

	Human(String name, SEX sex){
		this.setName(name);				// ���O�ݒ�
		this.setSex(sex);				// ���ʐݒ�
		this.myChara	= null;			// ���i������
		this.unlikeChara= null;			// ���Ȑ��i������
		this.associate	= null;			// ���ۑ��菉����
		this.marriage	= null;			// �������菉����
		this.childenNum	= 0;			// �q���̐�������

	}
	// �A�N�Z�b�T���\�b�h
	public charaType getChara(){return this.myChara;}					// ���i�擾
	public Human getAssociate(){return this.associate;}					// ���ۑ���擾
	public Human getMarriage(){return this.marriage;}					// ��������擾
	public void setAssociate(Human partner){this.associate = partner;}	// ���ۑ���o�^
	public void setMarriage(Human partner){this.marriage = partner;}	// ��������o�^
	// �t���������胁�\�b�h
	public boolean love(Animal animal){
		if ( animal.getClass()==this.getClass()&&
			(animal.getAge() < (this.getAge() + this.getAge()/3))||
			(((Human)animal).getChara()!=this.unlikeChara)){
			return true;
		}
		return false;
	}
	// �C�x���g����
	public int event(){
		// �����Œ�C�x���g
		switch(this.getAge()){
		case   0:msg("�����܂�܂����B");break;
		case   6:msg("�����w���ɂȂ�܂����B");break;
		case  12:msg("�����w���ɂȂ�܂����B");break;
		case  15:msg("�����Z���ɂȂ�܂����B");break;
		case  20:happen(eventType.CHARACTERIZATION,100);break;
		case  60:msg("���җ���}���܂����B");break;
		case 110:happen(eventType.DEAD,100);break;
		default:
		}
		// �������U�C�x���g
		switch(this.getAge()/10*10){	// 10�㍏�݂Ŗ��N����
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

	// �e��C�x���g����
	private boolean happen(eventType execEvent, int per){
		// �C�x���g�����m�F
		if ( world.successPercent(per) ||	// �C�x���g�G���J�E���g�m��
			 badStatus(execEvent))			// �ʔ��������E�m��
		{
			return false;
		}

		// �L���C�x���g�̓���
		switch(execEvent){
		case CHARACTERIZATION:	// ���i�`���C�x���g
			// �����_���ɐ��i���蓖��
			// =>Java�ł͗񋓑̂Ɛ��l���݊��Ŏg���Â炢�ׁA�z�񉻂��Ă��烉���_���ȗv�f�Ŋ��蓖�ĂĂ���B
			charaType[] charalist = charaType.values();
			this.myChara	= charalist[world.rnd.nextInt(charalist.length)];
			this.unlikeChara= charalist[world.rnd.nextInt(charalist.length)];
			msg("�́u"+this.myChara.toString()+"�v�Ȑ��i�̂悤�ł��B"
					+ "�u"+this.unlikeChara.toString()+"�v�Ȑl�Ƃ͂��܂葊���͗ǂ��Ȃ��悤�ł��B");
			break;

		case ASSOCIATE:	// ���ۃC�x���g
			Human target = (Human)world.matching(this);
			if (target==null ||						// �Ώۂ����Ȃ�
				this.love(target)==false)			// �����̍D�݂ł͂Ȃ�
			{
				msg("�͒N���ƕt�����������Ǝv���Ă���悤�ł��B");
			}
			else if (target.getAssociate()!=null)	// ���肪���ے�
			{
				msg("��"+target.getName()+"���D���ł������A����"+target.getAssociate().getName()+"�ƕt�������Ă����ג��߂��悤�ł��B");
			}
			else if (target.love(this)==false)		// ����̍D�݂ł͂Ȃ�
			{
				msg("��"+target.getName()+"�i"+target.getAge()+"�΁j�ɍ������ċʍӂ��܂����B");
			}
			else									// ���ۏ����N���A
			{
				if (this.marriage!=null ||			// ���肪������
					target.getMarriage()!=null)		// ���肪������
				{
					msg("��"+target.getName()+"�i"+target.getAge()+"�΁j�ƕs�ϒ��̂悤�ł��B");
				}else{
					msg("��"+target.getName()+"�i"+target.getAge()+"�΁j�ƕt�������n�߂܂����B");
				}
				this.associate = target;
				target.setAssociate(this);
			}
			break;

		case BREAKUP:	// �ʂ�C�x���g
			msg("��"+this.associate.getName()+"�i"+this.associate.getAge()+"�΁j�ƕʂ�܂����B");
			this.associate.setAssociate(null);
			this.associate = null;
			break;

		case MARRIAGE:	// �����C�x���g
			msg("��"+this.associate.getName()+"�i"+this.associate.getAge()+"�΁j���������܂����I");
			this.marriage	= this.associate;
			this.associate.setMarriage(this);
			this.associate.setAssociate(null);
			break;

		case DIVORCE:	// �����C�x���g
			msg("���������܂����E�E�E�B");
			this.marriage.setMarriage(null);			// ����̌������肩�玩����j��
			this.marriage=null;							// �����̌������肩�瑊���j��
			break;

		case CHAILDBORN:	// �o�Y�C�x���g
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
			msg("��"+childenNum+"�l�ڂ̎q�����o���܂����B���O��"+babyName+"�ł��B");
			Human baby = new Human(babyName, babySex);
			world.setBorn(baby);						// �o�Y�F���E�Ɏq����ǉ�
			break;

		case DEAD:	// ���S�C�x���g
			msg("�����炩�Ȗ���ɂ��܂����E�E�E�B");
			this.setDead();
		    world.setDead(this);
			break;
		}
		return true;
	}

	// �e��C�x���g���s����
	private boolean badStatus(eventType eventstatus){
		if (this.getLive() == false){		// ���S�t���O�őS�C�x���g����
			return true;
		}
		// �C�x���g�ʔ���
		switch(eventstatus){
		case CHARACTERIZATION:
			if(this.myChara!=null){			// ���i�t���C�x���g�͊����ɍs���Ă���Ζ���
				return true;
			}
			break;
		case ASSOCIATE:
			if(this.associate!=null){		// ���ۃC�x���g�͌��ے��Ŗ���
				return true;
			}
			if( this.marriage!=null &&		// �����ς��Ɗm���ŕs��
				world.successPercent(10))
			{
				return true;
			}
			break;
			
		case BREAKUP:
			if(this.associate==null){		// �ʂ�C�x���g�͌��ۖ����Ŗ���
				return true;
			}
			break;

		case MARRIAGE:
			if( this.marriage!=null ||		// �����C�x���g�͊��Ɍ�����
				this.associate==null)		// �܂��͌��ۑ���Ȃ��Ŗ���
			{	
				return true;
			}
			break;
		case DIVORCE:
			if(this.marriage==null){ 		// �����C�x���g�͖����Ŗ���
				return true;
			}
		break;
		case CHAILDBORN:					// �o�Y�C�x���g��
			if( this.getSex()==SEX.male	||	// �j 
				this.marriage==null		||	// ����
				this.childenNum >= 5 ){		// �q���T�l�ȏ�Ŗ���
				return true;
			}
			break;
		case DEAD:
			break;
		}
		return false;
	}
	
	// ���b�Z�[�W����
	private void msg(String str){
		if(this.getLive()==true){
			world.yearDisp();
			System.out.println("��"+this.getName() + "�i" + this.getAge() + "�΁j" + str) ;
		}
	}
	
	//���O�̃��X�g
	private String[] maleName={
		"�[",
		"��Y",
		"�k��",
		"�E�C",
		"���C",
		"�T��",
		"���`���[�h",
		"�W���j�[",
		"�W���b�N",
	};
	private String[] femaleName={
		"�p�q",
		"�Ԏq",
		"�m��",
		"����",
		"�䂫",
		"���C",
		"����",
		"�G���[",
		"�b��",
	};
};

