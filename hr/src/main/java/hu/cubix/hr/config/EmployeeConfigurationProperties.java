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
            private int topLimit;
            private int middleLimit;
            private int lowerLimit;

            private int percentageForTop;
            private int percentageForMiddle;
            private int percentageForLower;
            private int percentageForRest;

            public int getPercentageForTop() {
                return percentageForTop;
            }

            public void setPercentageForTop(int percentageForTop) {
                this.percentageForTop = percentageForTop;
            }

            public int getPercentageForMiddle() {
                return percentageForMiddle;
            }

            public void setPercentageForMiddle(int percentageForMiddle) {
                this.percentageForMiddle = percentageForMiddle;
            }

            public int getPercentageForLower() {
                return percentageForLower;
            }

            public void setPercentageForLower(int percentageForLower) {
                this.percentageForLower = percentageForLower;
            }

            public int getPercentageForRest() {
                return percentageForRest;
            }

            public void setPercentageForRest(int percentageForRest) {
                this.percentageForRest = percentageForRest;
            }


            public int getTopLimit() {
                return topLimit;
            }

            public void setTopLimit(int topLimit) {
                this.topLimit = topLimit;
            }

            public int getMiddleLimit() {
                return middleLimit;
            }

            public void setMiddleLimit(int middleLimit) {
                this.middleLimit = middleLimit;
            }

            public int getLowerLimit() {
                return lowerLimit;
            }

            public void setLowerLimit(int lowerLimit) {
                this.lowerLimit = lowerLimit;
            }


        }
    }
}
