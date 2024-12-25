package com.simplecoding.simpledms.controller.Toss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author : KTE
 * @fileName : TossController
 * @since : 24. 12. 11.
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TossController {
    @PostMapping("/api/payment/complete")
    public ResponseEntity<?> handlePaymentComplete(@RequestBody Map<String, Object> requestBody) {
        try {
            // 요청 데이터 파싱
            String paymentId = (String) requestBody.get("paymentId");
            Map<String, Object> order = (Map<String, Object>) requestBody.get("order");

            // 1. 포트원 결제내역 단건조회 API 호출
            // (외부 API 호출은 RestTemplate 또는 WebClient를 사용할 수 있음)
            String portOneApiSecret = "3PRhfkGuSzbexkTCSlFTVZZMpkKhQQa3Wmt42DnQWNiLYvBLSNHW2UqEQTVFPUI7PIV2NwcuJBtAB8bg"; // 환경 변수 또는 설정에서 불러오기
            String portOneApiUrl = "https://api.portone.io/payments/" + paymentId;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "PortOne " + portOneApiSecret);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    portOneApiUrl,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to fetch payment details");
            }

            Map<String, Object> paymentData = response.getBody();

            // 2. 고객사 내부 주문 데이터의 가격과 실제 지불된 금액 비교
            double orderAmount = Double.parseDouble(order.get("amount").toString());
            double paidAmount = Double.parseDouble(((Map) paymentData.get("amount")).get("total").toString());

            if (orderAmount == paidAmount) {
                String paymentStatus = (String) paymentData.get("status");

                switch (paymentStatus) {
                    case "VIRTUAL_ACCOUNT_ISSUED":
                        // 가상 계좌 발급 로직
                        break;
                    case "PAID":
                        // 결제 완료 로직
                        break;
                    default:
                        throw new RuntimeException("Unhandled payment status: " + paymentStatus);
                }

                return new ResponseEntity<>("Payment processed successfully", HttpStatus.OK);
            } else {
                // 금액 불일치 처리
                return new ResponseEntity<>("Payment amount mismatch", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // 예외 처리
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
