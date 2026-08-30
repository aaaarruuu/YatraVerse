# Contributing to YatraVerse AI

Thanks for working on YatraVerse AI. This document summarizes the Git workflow
already described in `README.md` so contributors have a quick reference
without hunting through the full doc.

## Branch Strategy

main
|__ develop
|__ feature/android-*
|__ feature/backend-*
|__ feature/llm-*
|__ feature/rag-*
|__ feature/<module>-<short-description>

- Never commit directly to main.
- develop is the integration branch.
- Each unit of work gets its own feature/* branch off develop.

## Workflow

1. git checkout develop && git pull origin develop
2. git checkout -b feature/<module>-<short-description>
3. Make your changes, keeping commits small and logical.
4. git status before staging, to review exactly what changed.
5. git add <specific files> (avoid blind git add . if unsure what's staged).
6. Commit using the convention below.
7. git push origin feature/<module>-<short-description>
8. Open a Pull Request into develop. Another team member reviews before merge.

## Commit Convention

| Type | Use for |
|------|---------|
| feat: | New feature |
| fix: | Bug fix |
| docs: | Documentation only |
| test: | Adding/updating tests |
| refactor: | Code change that neither fixes a bug nor adds a feature |
| chore: | Tooling, scaffolding, config, dependencies |

Example: feat: add JWT refresh token rotation

## Module Ownership

See the "Team Collaboration" section of README.md for the four module
areas (Android, Backend, LLM+RAG, ML/AI) and who owns each.

## Progress Tracking

This project maintains PROJECT_PROGRESS.md at the repo root. Update it
whenever you complete a meaningful step so any teammate can pick up your
work without needing to ask you directly.
