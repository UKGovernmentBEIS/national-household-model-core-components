;;; nhm.el --- Support for the national household model

;; Copyright (C) 2014 CSE

;; Author: Tom Hinton <tom.hinton@cse.org.uk>
;; Version: 1.1
;; Package-Requires: ((company "0.5") (flycheck "0.5"))
;; Keywords: nhm, mode

;;; Commentary:

;; This package provides a minor mode for editing NHM source files.  You will need to
;; customize the nhm-jar-path varaible to tell the checker where the NHM jar lives.

;;; Code:

(defgroup nhm nil
  "Tools for working with the national household model in emacs"
  :group 'extensions)

(defcustom nhm-jar-path nil
  "Path to the NHM CLI tools jar file."
  :group 'nhm
  :type '(string))

(defcustom
  nhm-repo-url
  "http://repo.research.cse.org.uk/"
  "Where to look on the internet for NHM jar files."
  :group 'nhm
  :type '(string))

(defvar nhm/syntax-table ())
(defvar nhm/commands ())

(require 'flycheck)

;;;###autoload
(define-derived-mode nhm-mode lisp-mode "nhm"
  "Major mode for the NHM"
  :syntax-table
  (let ((table (copy-syntax-table lisp-mode-syntax-table)))
    (modify-syntax-entry ?\[ "(]" table)
    (modify-syntax-entry ?\] ")[" table)
    table)

  (set (make-local-variable 'font-lock-defaults)
       '((
          ("\\w+:" . font-lock-keyword-face)
          ("\\[" . font-lock-constant-face)
          ("\\]" . font-lock-constant-face)
          ("(\\(\\w+\\)" 1 font-lock-function-name-face)
          )
         nil ;only kw
         nil ; case fold
         (("-.+-/*"."w")) ; syntax table
         backward-paragraph))

  ;; see also http://stackoverflow.com/questions/3623101/how-to-extend-emacs-lisp-mode-with-indentation-changes-and-color-changes
  )

(add-hook 'nhm-mode-hook 'nhm/setup-flycheck)
(add-hook 'nhm-mode-hook 'nhm/setup-company)
(add-hook 'nhm-mode-hook 'nhm/setup-imenu)

(define-key-after
  nhm-mode-map
  [menu-bar nhm-menu]
  (cons "NHM" (make-sparse-keymap))
  'tools)

(define-key
  nhm-mode-map
  [menu-bar nhm-menu run]
  '("Run buffer" . nhm/run-this-buffer))

(define-key
  nhm-mode-map
  [menu-bar nhm-menu expand]
  '("Expand templates" . nhm/view-expanded))


(define-key
  nhm-mode-map
  [menu-bar nhm-menu help]
  '("Help" . nhm/help-on-thing))

(define-key
  nhm-mode-map
  (kbd "<f5>") 'nhm/run-this-buffer)

(define-key
  nhm-mode-map
  (kbd "C-h n") 'nhm/help-on-thing)


(define-key
  nhm-mode-map
  (kbd "C-c t")
  'nhm/align-table)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;; MISC COMMANDS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defun nhm/align-table (beg end)
  (interactive "r")
  (align-regexp
   beg end
   "\\(\\s-+\\)" 1 align-default-spacing t)
  (indent-region beg end))

(defun nhm/install-jar ()
  "Look in a maven repository for some nhm jars"
  (interactive)

  (start-process "download-nhm-jar" "*download-nhm-jar*"
                 "mvn"
                 "org.apache.maven.plugins:maven-dependency-plugin:2.8:get"
                 "-U"
                 (concat "-DremoteRepositories=" nhm-repo-url)
                 "-Dartifact=uk.org.cse.nhm:clitools:LATEST:jar:jar-with-dependencies"
                 (concat "-Ddest=" nhm-jar-path)))

(defun nhm/view-expanded ()
  "Template expands the current file."
  (interactive)
  (with-current-buffer
      (nhm/command (concat "expanded form of " (buffer-file-name)) "expand" (buffer-file-name))
    (nhm-mode)
    (set-process-sentinel
     (get-buffer-process (current-buffer))
     (lambda (p e)       
       ))))

(defun nhm/command (name &rest args)
  "Run an nhm command.  NAME is the command to run, and ARGS are its arguments."
  (let
      ((output-buffer (generate-new-buffer name)))
    (apply #'start-process
           name
           output-buffer

           "java"
           "-Xmx3G"
           "-jar"
           
           nhm-jar-path
           args
           )
    (display-buffer output-buffer)
    output-buffer))

(defun nhm/run-scenario (filename)
  (nhm/command (concat "nhm run " filename)
               "run" filename (concat filename ".zip")))

(defun nhm/run-this-buffer ()
  (interactive)
  (if (buffer-file-name) 
      (nhm/run-scenario (buffer-file-name))
    (message "Buffer is not saved in any file")))

(defun nhm/help-on-thing ()
  (interactive)
  (let ((current-thing (symbol-name (nhm/enclosing-sexp))))
    (message (format "thing: %s" current-thing))
    (set-process-sentinel
     (get-buffer-process
      (nhm/command (concat "help on " current-thing)
                   "man" current-thing))
     (lambda (p e)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;; FLYCHECK ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defun nhm/setup-flycheck ()
  (interactive)
  (flycheck-mode)
  (flycheck-select-checker 'nhm-validator))


(flycheck-define-checker nhm-validator
  "NHM syntax validator"
  :command ("/usr/bin/java" "-jar" 
            (eval nhm-jar-path)
            "validate"
            source-inplace)
  :error-patterns
  ((error line-start (file-name) ":" line ":" column ":" (message) line-end))
  :modes (nhm-mode))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;; IMENU ; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defun nhm/setup-imenu ()
  (interactive)
  (setq imenu-generic-expression
        '(("templates" "(template[[:space:]]+\\([^[:space:]]+\\)" 1)
          ("modules" "(~module[[:space:]]+\\([^[:space:]]+\\)" 1)
          ("variables" "(def[[:space:]]+\\([^[:space:]]+\\)" 1)
          ("actions" "(def-action[[:space:]]+\\([^[:space:]]+\\)" 1)
          ("tests" "(def-test[[:space:]]+\\([^[:space:]]+\\)" 1)
          )))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;; COMPANY ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defun nhm/setup-company ()
  (interactive)
  (if (not nhm/syntax-table) (nhm/load-syntax))
  (company-mode)
  (make-local-variable 'company-backends)
  (setf company-backends '(nhm-company-backend company-dabbrev-code)))

(defun nhm/load-syntax ()
  (interactive)
      (with-temp-message
        "Loading nhm syntax table (only happens once)..."
      
      (with-temp-buffer
        (call-process-shell-command (format "/usr/bin/java -jar %s lang"
                                            nhm-jar-path)
                                    nil t)
        (setf nhm/syntax-table (read (buffer-string)))
        (setf nhm/commands (mapcar (lambda (s) (symbol-name (car s))) nhm/syntax-table))
        ))
  )

(defun nhm-company-backend (command &optional arg &rest ignore)
  (interactive (list 'interactive))

  (case command
    (interactive (company-begin-backend 'nhm-company-backend))
    (no-cache t)
    (prefix (and (eq major-mode 'nhm-mode)
               (company-grab-symbol)))
    (candidates (nhm-suggest arg)
                )))

(defun nhm/suggest-command (prefix)
  (remove-if-not
   (lambda (candidate) (string-prefix-p prefix candidate))
   nhm/commands))

(defun nhm/suggest-argument (element prefix)
  (let ((element-def (assoc element nhm/syntax-table))) ;;lookup the element in the syntax rules
    (when element-def
      (let ((argument-names (mapcar (lambda (s) (symbol-name (car s))) (cdr element-def))))
        (remove-if-not
         (lambda (candidate) (string-prefix-p prefix candidate))
         argument-names)
        ))))

(defun nhm-suggest (prefix)
  (let ((here (nhm/enclosing-sexp)))
    (if (string-equal here prefix)
        (nhm/suggest-command prefix)
      (if here
          ;; there is an enclosing sexp but it's not the prefix
          ;; so look for an argument with the right name
          (nhm/suggest-argument here prefix)
          )
      )))

(defun nhm/enclosing-sexp ()
  (interactive)
  (condition-case ex
      (save-excursion
        (up-list)
        (backward-sexp)
        (forward-char)
        (symbol-at-point))
    ('error nil)))

(provide 'nhm-mode)

;;; nhm.el ends here
