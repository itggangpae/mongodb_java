package mongodb_java;

import java.util.List;
import java.util.Scanner;
import org.bson.Document;
public class GoodMain {
	public static void main(String[] args) {
		// 키보드 입력 객체 만들기
		Scanner sc = new Scanner(System.in);

		// 각 열의 값을 저장할 변수
		String code = null;
		String name = null;
		String manufacture = null;
		String imsi = null;
		int price = 0;

		// 데이터베이스 작업 결과를 저장할 변수
		Boolean result = null;
		Document document = null;

		MainLoop: while (true) {
			// 메뉴 출력과 입력 받기
			System.out.print("1.데이터 전체 가져오기 2.데이터 1개 가져오기 3.데이터 추가 4.데이터 수정 5.데이터 삭제 6.프로그램 종료:");
			String menu = sc.nextLine();
			// 데이터베이스 연동 인스턴스 만들기
			GoodDAO goodDao = GoodDAO.getInstance();
			switch (menu) {
			case "1":
				List<Document> list = goodDao.allGoods();
				for(Document doc : list) {
					System.out.println(doc.get("code") + ":" + doc.get("name")+":" + doc.get("price"));
				}
				break;

			case "2":
				System.out.print("조회할 데이터의 코드를 입력하세요:");
				code = sc.nextLine();
				document = goodDao.getGood(code);
				if(document == null) {
					System.out.println("조회할려고 하는 코드가 없습니다.");
				}else {
					System.out.println(document);
				}
				break;

			case "3":
				System.out.print("삽입할 데이터의 코드를 입력하세요:");
				code = sc.nextLine();
				document = goodDao.getGood(code);
				if (document == null) {
					System.out.println("사용 가능한 코드입니다.");
					System.out.print("이름을 입력하세요:");
					name = sc.nextLine();
					System.out.print("원산지를 입력하세요:");
					manufacture = sc.nextLine();
					System.out.print("가격을 입력하세요:");
					imsi = sc.nextLine();
					try {
						price = Integer.parseInt(imsi);
						document = new Document();
						document.append("code", code);
						document.append("name", name);
						document.append("manufacture", manufacture);
						document.append("price", price);
						result = goodDao.insertGood(document);
						if (result == true) {
							System.out.println("데이터 삽입에 성공하셨습니다.");
						} else {
							System.out.println("데이터 삽입에 실패하셨습니다.");			}
					} catch (Exception e) {
						System.out.println("잘못된 가격을 입력하셨습니다.");
					}
				} else {
					System.out.println("사용 불가능한 코드입니다.");
				}
				break;

			case "4":
				System.out.print("수정할 데이터의 코드를 입력하세요:");
				code = sc.nextLine();
				document = goodDao.getGood(code);
				if (document != null) {
					System.out.println("수정 가능한 코드입니다.");
					System.out.print("이름을 입력하세요:");
					name = sc.nextLine();
					System.out.print("원산지를 입력하세요:");
					manufacture = sc.nextLine();
					System.out.print("가격을 입력하세요:");
					imsi = sc.nextLine();
					try {
						price = Integer.parseInt(imsi);
						document.append("code", code);
						document.append("name", name);
						document.append("manufacture", manufacture);
						document.append("price", price);
						result = goodDao.updateGood(document);

						if (result == true) {
							System.out.println("데이터 수정에 성공하셨습니다.");
						} else {
							System.out.println("데이터 수정에 실패하셨습니다.");
						}
					} catch (Exception e) {
						System.out.println("잘못된 가격을 입력하셨습니다.");
					}
				} else {
					System.out.println("존재하지 않는 코드라서 수정할 수 없습니다.");
				}
				break;
			case "5":
				System.out.print("삭제할 데이터의 코드를 입력하세요:");
				code = sc.nextLine();
				document = goodDao.getGood(code);
				if (document != null) {
					System.out.println("삭제 가능한 코드입니다.");
					System.out.print("정말로 삭제하시겠습니까?(삭제:Y)");
					String r = sc.nextLine();
					if (r.toUpperCase().equals("Y")) {
						result = goodDao.deleteGood(code);
						if (result == true) {
							System.out.println("삭제에 성공하셨습니다..");
						} else {
							System.out.println("삭제에 실패하셨습니다..");
						}
					}
				} else {
					System.out.println("존재하지 않는 코드라서 삭제할 수 없습니다.");
				}
				break;
			case "6":
				break MainLoop;
			default:
				System.out.println("메뉴를 잘못 선택하셨습니다.");
				break;
			}
			System.out.println("엔터를 누르면 메인 메뉴로 이동합니다.");
			sc.nextLine();
		}
		System.out.println("프로그램을 종료합니다!!!!");
		sc.close();
	}
}
