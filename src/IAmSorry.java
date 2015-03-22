public class IAmSorry {
	public static void main(String[] args) {
		Boy boy = new Boy("东哥");
		Girl girl = new Girl("小萌神");

		boy.chatting(girl);
		girl.chatting(boy);
		boy.chatting(girl);
		girl.chatting(boy);
		boy.chatting(girl);
		girl.chatting(boy);
		boy.挖鼻孔();
		boy.挖鼻孔();
		boy.挖鼻孔();
		girl.isLike(boy);
		while (!girl.likeYou) {
			boy.sorry(girl);
		}
		System.out.println("谢天谢地");
	}
}

class Boy{
	int 挖鼻孔的次数 = 0;
	String name;

	public Boy(String name) {
		this.name = name;
	}

	public void 挖鼻孔() {
		System.out.println("挖一次鼻孔--深深的");
		挖鼻孔的次数++;
	}

	public void sorry(Girl girl) {
		if (!girl.likeYou) {
			System.out.println("如果挖鼻孔也是一种错，我诚心诚意的认错！");
			girl.giveChance(this);
		}
	}

	public void chatting(Girl girl) {
		System.out.println("chatting with girl named " + girl.name);
	}
}

class Girl{

	boolean likeYou = true;
	String name;

	public Girl(String name) {
		this.name = name;
	}

	public void isLike(Boy boy) {
		if (boy.挖鼻孔的次数 > 2) {
			System.out.println("你怎么老是挖鼻孔, 不喜欢你了");
			likeYou = false;
		} else {
			System.out.println("次数不多我可以忍受");
		}
	}

	public void giveChance(Boy boy) {
		if (Math.random() > 0.5) {
			this.likeYou = true;
			System.out.println("既然你诚心诚意的认错，那我就大方的原谅你");
		}
	}

	public void chatting(Boy boy) {
		System.out.println("chatting with boy named " + boy.name);
	}
}