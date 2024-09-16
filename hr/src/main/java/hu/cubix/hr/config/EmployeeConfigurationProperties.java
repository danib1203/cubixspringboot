package hu.cubix.hr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "hr")
@Component
public class EmployeeConfigurationProperties {
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static class Employee {

        private Smart smart;

        public Smart getSmart() {
            return smart;
        }

        public void setSmart(Smart smart) {
            this.smart = smart;
        }

        public static class Smart {
            private double topLimit;
            private double middleLimit;
            private double lowerLimit;

            private double percentageForTop;
            private double percentageForMiddle;
            private double percentageForLower;
            private double percentageForRest;

            public double getTopLimit() {
                return topLimit;
            }

            public void setTopLimit(double topLimit) {
                this.topLimit = topLimit;
            }

            public double getMiddleLimit() {
                return middleLimit;
            }

            public void setMiddleLimit(double middleLimit) {
                this.middleLimit = middleLimit;
            }

            public double getLowerLimit() {
                return lowerLimit;
            }

            public void setLowerLimit(double lowerLimit) {
                this.lowerLimit = lowerLimit;
            }

            public double getPercentageForTop() {
                return percentageForTop;
            }

            public void setPercentageForTop(double percentageForTop) {
                this.percentageForTop = percentageForTop;
            }

            public double getPercentageForMiddle() {
                return percentageForMiddle;
            }

            public void setPercentageForMiddle(double percentageForMiddle) {
                this.percentageForMiddle = percentageForMiddle;
            }

            public double getPercentageForLower() {
                return percentageForLower;
            }

            public void setPercentageForLower(double percentageForLower) {
                this.percentageForLower = percentageForLower;
            }

            public double getPercentageForRest() {
                return percentageForRest;
            }

            public void setPercentageForRest(double percentageForRest) {
                this.percentageForRest = percentageForRest;
            }
        }
    }
}
