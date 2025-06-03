import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ducct.example.InsuranceClaim;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceClaimTest {

    private InsuranceClaim claim;

    @BeforeEach
    void setUp() {
        claim = new InsuranceClaim("C001", 1000.0);
    }

    @Test
    @DisplayName("Constructor initializes correctly")
    void testConstructorInitializesValues() {
        assertEquals("C001", claim.getClaimId());
        assertEquals(1000.0, claim.getAmount());
        assertEquals("Pending", claim.getClaimStatus());
    }

    @Test
    @DisplayName("Constructor throws exception for invalid amount")
    void testConstructorInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> new InsuranceClaim("C002", -500));
    }

    @Test
    @DisplayName("processClaim updates status if Pending")
    void testProcessClaimWhenPending() {
        boolean result = claim.processClaim("Approved");
        assertTrue(result);
        assertEquals("Approved", claim.getClaimStatus());
    }

    @Test
    @DisplayName("processClaim returns false if not Pending")
    void testProcessClaimWhenNotPending() {
        claim.processClaim("Approved");
        boolean result = claim.processClaim("Rejected");
        assertFalse(result);
        assertEquals("Approved", claim.getClaimStatus());
    }

    @Test
    @DisplayName("calculatePayout returns correct amount when Approved")
    void testCalculatePayoutApproved() {
        claim.processClaim("Approved");
        assertEquals(850.0, claim.calculatePayout(), 0.001);
    }

    @Test
    @DisplayName("calculatePayout returns 0 if not Approved")
    void testCalculatePayoutNotApproved() {
        assertEquals(0, claim.calculatePayout());
    }

    @Test
    @DisplayName("updateClaimAmount updates successfully")
    void testUpdateClaimAmount() {
        claim.updateClaimAmount(2000.0);
        assertEquals(2000.0, claim.getAmount());
    }

    @Test
    @DisplayName("updateClaimAmount throws exception for invalid amount")
    void testUpdateClaimAmountInvalid() {
        assertThrows(IllegalArgumentException.class, () -> claim.updateClaimAmount(0));
    }

    @ParameterizedTest
    @CsvSource({
            "Approved,850.0",
            "Rejected,0",
            "Pending,0"
    })
    @DisplayName("Parameterized Test - calculatePayout for various statuses")
    void testCalculatePayoutVariousStatuses(String status, double expectedPayout) {
        claim.processClaim(status);
        assertEquals(expectedPayout, claim.calculatePayout(), 0.001);
    }

    @Test
    @DisplayName("toString returns expected format")
    void testToStringFormat() {
        String output = claim.toString();
        assertTrue(output.contains("InsuranceClaim"));
        assertTrue(output.contains("claimId='C001'"));
        assertTrue(output.contains("amount=1000.0"));
        assertTrue(output.contains("claimStatus='Pending'"));
    }

    @Test
    @DisplayName("Constructor throws exception for null claim ID")
    void testConstructorNullClaimId() {
        assertThrows(IllegalArgumentException.class, () -> new InsuranceClaim(null, 1000.0));
    }

    @Test
    @DisplayName("processClaim throws exception for null input")
    void testProcessClaimNullInput() {
        assertThrows(IllegalArgumentException.class, () -> claim.processClaim(null));
    }
}
