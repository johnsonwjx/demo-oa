package xiong.domain;

/**
 * @author xiong
 *
 */
public enum Gender {
	Male {
		@Override
		public String getGender() {
			return "女";
		}
	}, Female {
		@Override
		public String getGender() {
			return "男";
		}
	};
	public abstract String getGender() ;
}
