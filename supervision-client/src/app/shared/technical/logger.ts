export class Logger {
    
        public static LOG: number = 5;
        public static DEBUG: number = 4;    
        public static INFO: number = 3;
        public static WARN: number = 2;
        public static ERROR: number = 1;
        public static FATAL: number = 0;
    
        level: number = Logger.INFO;
    
        constructor(level: number) {
            this.level = level;
        }
    
        f(text: string, value?): void {
            this.doLogError(text, Logger.FATAL, 'FATAL', value);
        }
    
        e(text: string, value?): void {
            this.doLogError(text, Logger.ERROR, 'ERROR', value);
        }
    
        w(text: string, value?): void {
            this.doLog(text, Logger.WARN, 'WARN', value);
        }
    
        i(text: string, value?): void {
            this.doLog(text, Logger.INFO, 'INFO', value);
        }
    
        d(text: string, value?): void {
            this.doLog(text, Logger.DEBUG, 'DEBUG', value);
        }
    
        l(text: string, value?): void {
            this.doLog(text, Logger.LOG, 'LOG', value);
        }
    
        doLog(text: string, level: number, prefix: string, value?) {
            if (this.level >= level) {
                if (value) {
                    console.log(prefix + ' - ' + text);
                    console.log(value);
                } else {
                    console.log(prefix + ' - ' + text);
                }
            }
        }
    
        doLogError(text: string, level: number, prefix: string, value?) {
            if (this.level >= level) {
                if (value) {
                    console.error(prefix + ' - ' + text);
                    console.error(value);
                } else {
                    console.error(prefix + ' - ' + text);
                }
            }
        }
    
    }
    